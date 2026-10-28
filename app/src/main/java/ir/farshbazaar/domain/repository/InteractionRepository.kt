package ir.farshbazaar.domain.repository

import ir.farshbazaar.data.local.dao.InteractionDao
import ir.farshbazaar.data.local.dao.UserDao
import ir.farshbazaar.data.local.entity.CommentEntity
import ir.farshbazaar.data.local.entity.FollowEntity
import ir.farshbazaar.data.local.entity.LikeEntity
import ir.farshbazaar.data.local.entity.XpEventEntity
import ir.farshbazaar.domain.model.Comment
import ir.farshbazaar.domain.model.XpEventType
import ir.farshbazaar.domain.xp.XpEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.UUID

interface InteractionRepository {
    suspend fun toggleLike(userId: String, rugId: String)
    fun observeLikedRugs(userId: String): Flow<List<String>>
    fun observeLikeCount(rugId: String): Flow<Int>
    fun isLiked(userId: String, rugId: String): Flow<Boolean>
    fun observeComments(rugId: String): Flow<List<Comment>>
    suspend fun addComment(rugId: String, authorId: String, text: String)
    fun observeFollowing(userId: String): Flow<List<String>>
    fun isFollowing(followerId: String, followedId: String): Flow<Boolean>
    suspend fun toggleFollow(followerId: String, followedId: String)
    fun observeTotalXp(userId: String): Flow<Int>
    suspend fun awardXp(userId: String, type: XpEventType)
}

class RoomInteractionRepository(
    private val interactionDao: InteractionDao,
    private val userDao: UserDao
) : InteractionRepository {

    override suspend fun toggleLike(userId: String, rugId: String) {
        val currentlyLiked = interactionDao.isLiked(userId, rugId).firstOrNull() ?: false
        if (currentlyLiked) {
            interactionDao.deleteLike(userId, rugId)
        } else {
            interactionDao.insertLike(LikeEntity(userId = userId, rugId = rugId))
            // Award XP for LIKE_RECEIVED
            awardXp(userId, XpEventType.LIKE_RECEIVED)
        }
    }

    override fun observeLikedRugs(userId: String): Flow<List<String>> {
        return interactionDao.observeLikedRugs(userId)
    }

    override fun observeLikeCount(rugId: String): Flow<Int> {
        return interactionDao.observeLikeCount(rugId)
    }

    override fun isLiked(userId: String, rugId: String): Flow<Boolean> {
        return interactionDao.isLiked(userId, rugId)
    }

    override fun observeComments(rugId: String): Flow<List<Comment>> {
        return interactionDao.observeComments(rugId).map { list ->
            list.map { entity ->
                val author = userDao.get(entity.authorId)
                Comment(
                    id = entity.id,
                    rugId = entity.rugId,
                    authorId = entity.authorId,
                    authorName = author?.displayName ?: "کاربر گرامی",
                    text = entity.text,
                    createdAt = entity.createdAt,
                    isDemo = entity.isDemo
                )
            }
        }
    }

    override suspend fun addComment(rugId: String, authorId: String, text: String) {
        if (text.isBlank()) return
        interactionDao.insertComment(
            CommentEntity(
                id = "c_${UUID.randomUUID()}",
                rugId = rugId,
                authorId = authorId,
                text = text.trim()
            )
        )
    }

    override fun observeFollowing(userId: String): Flow<List<String>> {
        return interactionDao.observeFollowing(userId)
    }

    override fun isFollowing(followerId: String, followedId: String): Flow<Boolean> {
        return interactionDao.isFollowing(followerId, followedId)
    }

    override suspend fun toggleFollow(followerId: String, followedId: String) {
        val currentlyFollowing = interactionDao.isFollowing(followerId, followedId).firstOrNull() ?: false
        if (currentlyFollowing) {
            interactionDao.deleteFollow(followerId, followedId)
        } else {
            interactionDao.insertFollow(FollowEntity(followerId = followerId, followedId = followedId))
        }
    }

    override fun observeTotalXp(userId: String): Flow<Int> {
        return interactionDao.observeTotalXp(userId).map { it ?: 0 }
    }

    override suspend fun awardXp(userId: String, type: XpEventType) {
        val points = XpEngine.calculatePoints(type)
        if (points <= 0) return
        interactionDao.insertXpEvent(
            XpEventEntity(
                id = "xp_${UUID.randomUUID()}",
                userId = userId,
                type = type.name,
                basePoints = type.basePoints,
                multiplier = XpEngine.collaborationMultiplier()
            )
        )
    }
}
