package com.chekh.paysage.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import com.chekh.paysage.core.provider.ui
import com.chekh.paysage.core.ui.tools.ActionDelay
import kotlinx.coroutines.*

class LongClickableCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    private var onLongClickListener: OnLongClickListener? = null
    private var delay = ActionDelay()

    init {
        isLongClickable = true
        isClickable = true
        isFocusable = true
    }

    override fun setOnLongClickListener(
        onLongClickListener: OnLongClickListener?
    ) {
        this.onLongClickListener = onLongClickListener
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (onLongClickListener == null) {
            return super.onInterceptTouchEvent(event)
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                delay.start(ui, CLICK_DURATION) {
                    onLongClickListener?.let {
                        performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                        it.onLongClick(this@LongClickableCardView)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> Unit
            else -> delay.cancel()
        }
        return false
    }

    private companion object {
        const val CLICK_DURATION = 500L
    }
}
