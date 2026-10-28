package ir.farshbazaar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val displayName: String,
    val bio: String,
    val isSeller: Boolean,
    val isDemo: Boolean = true
)

@Entity(tableName = "rugs")
data class RugEntity(
    @PrimaryKey val id: String,
    val sellerId: String,
    val title: String,
    val description: String,
    val category: String, // HANDMADE_RUG, MACHINE_RUG, SERVICES, etc.
    val region: String,
    val era: String = "معاصر",
    val price: Long?,
    val createdAt: Long = System.currentTimeMillis(),
    val isDemo: Boolean = true
)

@Entity(tableName = "inspection_indicators")
data class InspectionIndicatorEntity(
    @PrimaryKey val id: String,
    val titleFa: String,
    val titleEn: String,
    val bodyFa: String,
    val bodyEn: String,
    val category: String = "quality",
    val displayOrder: Int = 0,
    val isActive: Boolean = true,
    val isDemo: Boolean = true
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val rugId: String,
    val authorId: String,
    val text: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isDemo: Boolean = true
)

@Entity(tableName = "likes", primaryKeys = ["userId", "rugId"])
data class LikeEntity(
    val userId: String,
    val rugId: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "follows", primaryKeys = ["followerId", "followedId"])
data class FollowEntity(
    val followerId: String,
    val followedId: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "checklist_states", primaryKeys = ["rugId", "indicatorId"])
data class ChecklistStateEntity(
    val rugId: String,
    val indicatorId: String,
    val checked: Boolean,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "xp_events")
data class XpEventEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val type: String,
    val basePoints: Int,
    val multiplier: Int = 1,
    val createdAt: Long = System.currentTimeMillis()
)
