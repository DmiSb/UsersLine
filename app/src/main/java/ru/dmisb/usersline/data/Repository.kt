package ru.dmisb.usersline.data

import ru.dmisb.usersline.data.db.ADatabase

class Repository(private val db: ADatabase) {
    fun getAllUsers() = db.userDao().selectAll()
    fun getUser(userId: String) = db.userDao().selectUser(userId)
}