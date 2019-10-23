package ru.dmisb.usersline.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.dmisb.usersline.data.Repository
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent {

    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    protected val repo by inject<Repository>()

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    suspend fun  <T> onDefContext(
        block: suspend CoroutineScope.() -> T
    ) : T {
        return withContext(Dispatchers.Default) {
            block.invoke(this)
        }
    }

    suspend fun  <T> onMainContext(
        block: suspend CoroutineScope.() -> T
    ) : T {
        return withContext(Dispatchers.Main) {
            block.invoke(this)
        }
    }
}