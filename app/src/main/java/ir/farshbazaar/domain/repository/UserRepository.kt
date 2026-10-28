package ir.farshbazaar.domain.repository

import ir.farshbazaar.data.local.dao.UserDao
import ir.farshbazaar.data.local.entity.UserEntity
import ir.farshbazaar.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface UserRepository {
    fun observe(id: String): Flow<User?>
    suspend fun get(id: String): User?
}

class RoomUserRepository(private val userDao: UserDao) : UserRepository {
    override fun observe(id: String): Flow<User?> {
        return userDao.observe(id).map { it?.toDomain() }
    }

    override suspend fun get(id: String): User? {
        return userDao.get(id)?.toDomain()
    }
}

private fun UserEntity.toDomain() = User(
    id = id,
    displayName = displayName,
    bio = bio,
    isSeller = isSeller,
    isDemo = isDemo
)
