package ru.dmisb.usersline.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import org.koin.core.inject
import ru.dmisb.usersline.common.BaseViewModel
import ru.dmisb.usersline.data.api.startLoadUsers
import ru.dmisb.usersline.data.db.entity.User
import ru.dmisb.usersline.utils.Const

class UsersViewModel : BaseViewModel() {

    private val workManager by inject<WorkManager>()

    private val users : LiveData<List<User>> = repo.getAllUsers()
    private val loading = MutableLiveData<Boolean>()

    private var offset = 0
    private var total = 0

    init {
        loadUsers()
    }

    fun getUsers() = users

    fun isLoading() : LiveData<Boolean> = loading

    fun loadUsers(position: Int) {
        if (position > offset - Const.USER_PART_COUNT && offset < total && loading.value != true) {
            loadUsers()
        }
    }

    fun refresh() {
        offset = 0
        loadUsers()
    }

    private fun loadUsers() {
        loading.value = true
        workManager.startLoadUsers(offset).observeForever {
            when (it.state) {
                WorkInfo.State.SUCCEEDED -> {
                    loading.value = false
                    offset = it.outputData.getInt(Const.EXTRAS_COUNT, 0) +
                            it.outputData.getInt(Const.EXTRAS_OFFSET, 0)
                    total = it.outputData.getInt(Const.EXTRAS_TOTAL, 0)
                }
                WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {
                    loading.value = false
                }
                else -> {}
            }
        }
    }
}