package com.chekh.paysage.core.ui.listener

import android.view.GestureDetector
import android.view.MotionEvent

interface SimpleGestureListener : GestureDetector.OnGestureListener {

    override fun onDown(e: MotionEvent) = false

    override fun onShowPress(e: MotionEvent) {}

    override fun onSingleTapUp(e: MotionEvent) = false

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ) = false

    override fun onLongPress(e: MotionEvent) {}

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ) = false
}
