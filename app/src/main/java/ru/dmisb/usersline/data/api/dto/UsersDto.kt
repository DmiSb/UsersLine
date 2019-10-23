package ru.dmisb.usersline.data.api.dto

import com.google.gson.annotations.SerializedName

data class MembersDto(
    val members: List<UserDto>,
    val count: Int,
    val offset: Int,
    val success: Boolean,
    val total: Int
)

data class UserDto(
    @SerializedName("_id")
    val id: String,

    val status: String,
    val name: String,

    @SerializedName("username")
    val userName: String? = null,

    val utcOffset: Float? = null
)