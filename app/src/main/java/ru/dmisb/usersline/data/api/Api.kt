package ru.dmisb.usersline.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.dmisb.usersline.data.api.dto.MembersDto

interface Api {

    @GET("v1/channels.members?roomName=general")
    @Headers(value = [
        "X-User-Id: e2qQQA3adTsZGN8et",
        "X-Auth-Token: kc8AHBZlH3iCObf9alUrfpDkUoBAEsYoEEw3JBemQq3"
    ])
    fun loadUsers(
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ) : Call<MembersDto>
}