package com.chekh.paysage.ui.statusbar

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi

class MaterialStatusBarDecorator : StatusBarDecorator() {

    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            statusBarModeVersionO(activity, isDark)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBarModeVersionM(activity, isDark)
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun statusBarModeVersionO(activity: Activity, dark: Boolean) {
        if (dark) {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, true)
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, true)
        } else {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, false)
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarModeVersionM(activity: Activity, dark: Boolean) {
        if (dark) {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, true)
        } else {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, false)
        }
    }
}