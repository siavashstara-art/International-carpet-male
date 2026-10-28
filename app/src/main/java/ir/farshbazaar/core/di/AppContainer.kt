package ir.farshbazaar.core.di

import android.content.Context
import ir.farshbazaar.data.local.AppDatabase
import ir.farshbazaar.domain.repository.*

interface AppContainer {
    val database: AppDatabase
    val userRepository: UserRepository
    val rugRepository: RugRepository
    val indicatorRepository: IndicatorRepository
    val interactionRepository: InteractionRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val database: AppDatabase by lazy {
        AppDatabase.getInstance(context)
    }

    override val userRepository: UserRepository by lazy {
        RoomUserRepository(database.userDao())
    }

    override val indicatorRepository: IndicatorRepository by lazy {
        RoomIndicatorRepository(database.indicatorDao())
    }

    override val interactionRepository: InteractionRepository by lazy {
        RoomInteractionRepository(database.interactionDao(), database.userDao())
    }

    override val rugRepository: RugRepository by lazy {
        RoomRugRepository(database.rugDao(), database.indicatorDao(), database.interactionDao())
    }
}
