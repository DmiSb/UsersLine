package ru.dmisb.usersline.data.api

object HttpConfig {
    const val BASE_URL = "https://open.rocket.chat/api/"
    const val CONTENT_URL = "https://open.rocket.chat/avatar/%s?format=jpeg"
    const val MAX_CONNECTION_TIMEOUT = 30L
    const val MAX_READ_TIMEOUT = 60L
    const val MAX_WRITE_TIMEOUT = 60L
}