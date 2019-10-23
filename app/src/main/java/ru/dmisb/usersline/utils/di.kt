package ru.dmisb.usersline.utils

import androidx.room.Room
import androidx.work.WorkManager
import org.koin.dsl.module
import ru.dmisb.usersline.data.Repository
import ru.dmisb.usersline.data.api.Api
import ru.dmisb.usersline.data.api.HttpClient
import ru.dmisb.usersline.data.db.ADatabase

val dataModule = module {

    single { HttpClient() }

    single { get<HttpClient>().createService(Api::class.java) }

    single { Room.databaseBuilder(get(), ADatabase::class.java, "user_line.db")
        .fallbackToDestructiveMigration()
        .build()
    }

    single { Repository(get()) }

    single { WorkManager.getInstance(get()) }
}