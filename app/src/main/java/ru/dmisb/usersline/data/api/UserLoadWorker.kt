package ru.dmisb.usersline.data.api

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import ru.dmisb.usersline.data.db.ADatabase
import ru.dmisb.usersline.data.toUser
import ru.dmisb.usersline.utils.Const

class UserLoadWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams), KoinComponent {

    override fun doWork(): Result {
        val offset = inputData.getInt(Const.EXTRAS_OFFSET, 0)

        val api by inject<Api>()
        val db by inject<ADatabase>()

        try {
            val result = api.loadUsers(Const.USER_PART_COUNT, offset).execute()
            if (result.isSuccessful) {
                val body = result.body()
                if (body != null) {
                    if (body.members.isNotEmpty()) {
                        db.userDao().insertAll(body.members.map { it.toUser() })
                    }
                    val out = Data.Builder()
                        .putInt(Const.EXTRAS_OFFSET, body.offset)
                        .putInt(Const.EXTRAS_COUNT, body.count)
                        .putInt(Const.EXTRAS_TOTAL, body.total)
                        .build()

                    Thread.sleep(5000) //todo Пауза для тестирования загрузки при перевороте экрана
                    return Result.success(out)
                }
            }
            return Result.success()
        } catch (e: Exception) {
            return if (e is HttpException) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}

fun WorkManager.startLoadUsers(offset: Int) : LiveData<WorkInfo> {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val data = Data.Builder()
        .putInt(Const.EXTRAS_OFFSET, offset)
        .build()

    val request = OneTimeWorkRequest.Builder(UserLoadWorker::class.java)
        .setConstraints(constraints)
        .setInputData(data)
        .build()

    this.beginWith(request).enqueue()
    return this.getWorkInfoByIdLiveData(request.id)
}