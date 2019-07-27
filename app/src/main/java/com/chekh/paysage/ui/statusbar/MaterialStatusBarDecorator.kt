package com.chekh.paysage.ui.statusbar

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi

class MaterialStatusBarDecorator : StatusBarDecorator() {

    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> statusBarModeVersionO(activity, isDark)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> statusBarModeVersionM(activity, isDark)
            else -> statusBarModeVersionL(activity, isDark)
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun statusBarModeVersionO(activity: Activity, dark: Boolean) {
        if (dark) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarModeVersionM(activity: Activity, dark: Boolean) {
        if (dark) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        } else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    private fun statusBarModeVersionL(activity: Activity, dark: Boolean) {
        if (dark) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        } else {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }
}