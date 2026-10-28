package ir.farshbazaar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ir.farshbazaar.data.local.dao.*
import ir.farshbazaar.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        RugEntity::class,
        InspectionIndicatorEntity::class,
        CommentEntity::class,
        LikeEntity::class,
        FollowEntity::class,
        ChecklistStateEntity::class,
        XpEventEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun rugDao(): RugDao
    abstract fun indicatorDao(): IndicatorDao
    abstract fun interactionDao(): InteractionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "farshbazaar_v1.db"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed data asynchronously upon first database creation
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getInstance(context)
                            database.seedDatabase()
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()

                INSTANCE = instance
                // Also ensure seed is populated if database was empty
                CoroutineScope(Dispatchers.IO).launch {
                    instance.seedDatabaseIfEmpty()
                }
                instance
            }
        }
    }

    suspend fun seedDatabase() {
        userDao().insertAll(DemoSeed.users)
        indicatorDao().insertAll(DemoSeed.indicators)
        rugDao().insertAll(DemoSeed.rugs)
        interactionDao().insertAllComments(DemoSeed.sampleComments)
        for (like in DemoSeed.sampleLikes) {
            interactionDao().insertLike(like)
        }
        for (xp in DemoSeed.sampleXpEvents) {
            interactionDao().insertXpEvent(xp)
        }
    }

    suspend fun seedDatabaseIfEmpty() {
        if (userDao().count() == 0) {
            seedDatabase()
        }
    }
}
