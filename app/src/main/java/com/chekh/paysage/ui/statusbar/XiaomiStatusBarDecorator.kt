package com.chekh.paysage.ui.statusbar

import android.annotation.SuppressLint
import android.app.Activity

class XiaomiStatusBarDecorator(
    private val materialDecorator: MaterialStatusBarDecorator
) : StatusBarDecorator() {

    @SuppressLint("PrivateApi")
    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        val window = activity.window ?: return false
        return try {
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = window.javaClass.getMethod(
                "setExtraFlags",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            if (isDark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)
            }
            materialDecorator.statusBarDarkMode(activity, isDark)
            true
        } catch (e: Exception) {
            false
        }
    }
}