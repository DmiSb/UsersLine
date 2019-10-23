package ru.dmisb.usersline.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dmisb.usersline.data.db.dao.UserDao
import ru.dmisb.usersline.data.db.entity.User

@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ADatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
}
