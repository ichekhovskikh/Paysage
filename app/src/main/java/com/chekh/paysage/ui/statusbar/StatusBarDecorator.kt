package com.chekh.paysage.ui.statusbar

import android.app.Activity
import android.view.View
import android.view.WindowManager

abstract class StatusBarDecorator {

    abstract fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean

    fun setTransparentStatusBar(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val decorView = activity.window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_LAYOUT_FLAGS
    }
}