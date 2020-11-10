package com.chekh.paysage.core.ui.listener

import android.view.ScaleGestureDetector

interface SimpleScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {

    override fun onScale(detector: ScaleGestureDetector) = true

    override fun onScaleBegin(detector: ScaleGestureDetector) = true

    override fun onScaleEnd(detector: ScaleGestureDetector) {}
}
