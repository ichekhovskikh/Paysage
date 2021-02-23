package com.chekh.paysage.core.ui.statusbar

import android.app.Activity
import android.view.View
import android.view.WindowManager

abstract class StatusBarDecorator {

    abstract fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean

    @Suppress("DEPRECATION")
    fun setTransparentStatusBar(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_LAYOUT_FLAGS
    }

    @Suppress("DEPRECATION")
    protected fun setSystemUiVisibilityFlag(activity: Activity, bits: Int, enabled: Boolean) {
        var uiVisibility = activity.window.decorView.systemUiVisibility
        uiVisibility = if (enabled) uiVisibility or bits else uiVisibility and bits.inv()
        activity.window.decorView.systemUiVisibility = uiVisibility
    }
}
