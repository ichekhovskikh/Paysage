package com.chekh.paysage.ui.statusbar

import android.annotation.SuppressLint
import android.app.Activity

class XiaomiStatusBarDecorator(private var materialDecorator: MaterialStatusBarDecorator = MaterialStatusBarDecorator()) :
    StatusBarDecorator() {

    @SuppressLint("PrivateApi")
    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz = window.javaClass
            try {
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                val darkModeFlag = field.getInt(layoutParams)
                val extraFlagField =
                    clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (isDark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)
                }
                result = true
                materialDecorator.statusBarDarkMode(activity, isDark)
            } catch (e: Exception) {
            }
        }
        return result
    }
}