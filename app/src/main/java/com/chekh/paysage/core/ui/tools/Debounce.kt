package com.chekh.paysage.core.ui.tools

import android.os.SystemClock
import android.view.View

private const val DEFAULT_DELAY = 500L

class Debounce<T>(
    private val delayMillis: Long = DEFAULT_DELAY,
    private val action: (T) -> Unit,
) {
    private var lastClickedTime = 0L

    fun onEvent(value: T) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if (elapsedRealtime - lastClickedTime >= delayMillis) {
            lastClickedTime = elapsedRealtime
            action(value)
        }
    }
}

class OnDebounceClickListener(listener: View.OnClickListener) : View.OnClickListener {

    private val debounce = Debounce<View> { listener.onClick(it) }

    override fun onClick(view: View) = debounce.onEvent(view)
}
