package ru.dmisb.usersline.data

import ru.dmisb.usersline.data.api.dto.UserDto
import ru.dmisb.usersline.data.db.entity.User

fun UserDto.toUser() = User(
    id = id,
    name = name,
    status = status,
    utcOffset = utcOffset ?: 0f,
    userName = userName ?: ""
)