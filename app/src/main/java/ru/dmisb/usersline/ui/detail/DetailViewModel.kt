package ru.dmisb.usersline.ui.detail

import androidx.lifecycle.LiveData
import ru.dmisb.usersline.common.BaseViewModel
import ru.dmisb.usersline.data.db.entity.User

class DetailViewModel : BaseViewModel() {
    private lateinit var user: LiveData<User>

    fun init(userId: String) {
        user = repo.getUser(userId)
    }

    fun getUser() = user
}