package com.chekh.paysage.ui.statusbar

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import javax.inject.Inject

class MaterialStatusBarDecorator @Inject constructor() : StatusBarDecorator() {

    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            statusBarDarkModeVersionO(activity, isDark)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBarDarkModeVersionM(activity, isDark)
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun statusBarDarkModeVersionO(activity: Activity, isDark: Boolean) {
        statusBarDarkModeVersionM(activity, isDark)
        if (isDark) {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, true)
        } else {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarDarkModeVersionM(activity: Activity, isDark: Boolean) {
        if (isDark) {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, true)
        } else {
            setSystemUiVisibilityFlag(activity, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, false)
        }
    }
}