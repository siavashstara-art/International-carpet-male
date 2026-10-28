package ir.farshbazaar.data.local.dao

import androidx.room.*
import ir.farshbazaar.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun observe(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun get(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int
}

@Dao
interface RugDao {
    @Query("SELECT * FROM rugs ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<RugEntity>>

    @Query("SELECT * FROM rugs WHERE category = :category ORDER BY createdAt DESC")
    fun observeByCategory(category: String): Flow<List<RugEntity>>

    @Query("SELECT * FROM rugs WHERE sellerId = :sellerId ORDER BY createdAt DESC")
    fun observeBySeller(sellerId: String): Flow<List<RugEntity>>

    @Query("SELECT * FROM rugs WHERE id = :id LIMIT 1")
    fun observe(id: String): Flow<RugEntity?>

    @Query("SELECT * FROM rugs WHERE title LIKE '%' || :query || '%' OR region LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<RugEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rugs: List<RugEntity>)

    @Query("SELECT COUNT(*) FROM rugs")
    suspend fun count(): Int
}

@Dao
interface IndicatorDao {
    @Query("SELECT * FROM inspection_indicators WHERE isActive = 1 ORDER BY displayOrder ASC")
    fun observeAll(): Flow<List<InspectionIndicatorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(indicators: List<InspectionIndicatorEntity>)

    @Query("SELECT COUNT(*) FROM inspection_indicators")
    suspend fun count(): Int
}

@Dao
interface InteractionDao {
    @Query("SELECT rugId FROM likes WHERE userId = :userId")
    fun observeLikedRugs(userId: String): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM likes WHERE rugId = :rugId")
    fun observeLikeCount(rugId: String): Flow<Int>

    @Query("SELECT EXISTS(SELECT 1 FROM likes WHERE userId = :userId AND rugId = :rugId)")
    fun isLiked(userId: String, rugId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLike(like: LikeEntity)

    @Query("DELETE FROM likes WHERE userId = :userId AND rugId = :rugId")
    suspend fun deleteLike(userId: String, rugId: String)

    @Query("SELECT * FROM comments WHERE rugId = :rugId ORDER BY createdAt ASC")
    fun observeComments(rugId: String): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Query("SELECT followedId FROM follows WHERE followerId = :userId")
    fun observeFollowing(userId: String): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM follows WHERE followerId = :followerId AND followedId = :followedId)")
    fun isFollowing(followerId: String, followedId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollow(follow: FollowEntity)

    @Query("DELETE FROM follows WHERE followerId = :followerId AND followedId = :followedId")
    suspend fun deleteFollow(followerId: String, followedId: String)

    @Query("SELECT * FROM checklist_states WHERE rugId = :rugId")
    fun observeChecklist(rugId: String): Flow<List<ChecklistStateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklistState(state: ChecklistStateEntity)

    @Query("SELECT SUM(basePoints * multiplier) FROM xp_events WHERE userId = :userId")
    fun observeTotalXp(userId: String): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXpEvent(event: XpEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(comments: List<CommentEntity>)
}
