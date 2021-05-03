package com.chekh.paysage.core.ui.tools

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ActionDelay {

    private var delayedJob: Job? = null

    fun start(
        context: CoroutineContext = EmptyCoroutineContext,
        delayMillis: Long = DEFAULT_DELAY,
        action: suspend CoroutineScope.() -> Unit
    ) {
        if (delayedJob == null || delayedJob?.isCompleted == true) {
            delayedJob = GlobalScope.launch(context) {
                delay(delayMillis)
                action()
            }
        }
    }

    fun cancel() {
        delayedJob?.cancel()
    }

    private companion object {
        const val DEFAULT_DELAY = 500L
    }
}
