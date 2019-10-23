package ru.dmisb.usersline.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: String,

    val name: String,
    val status: String,
    val userName: String,
    val utcOffset: Float
)