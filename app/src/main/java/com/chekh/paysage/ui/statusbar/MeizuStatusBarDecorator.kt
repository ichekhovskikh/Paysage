package com.chekh.paysage.ui.statusbar

import android.app.Activity
import android.view.WindowManager.LayoutParams

class MeizuStatusBarDecorator : StatusBarDecorator() {

    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean = try {
        val attributes = activity.window.attributes
        val darkFlag = LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
        val meizuFlags = LayoutParams::class.java.getDeclaredField("meizuFlags")
        darkFlag.isAccessible = true
        meizuFlags.isAccessible = true
        val bit = darkFlag.getInt(null)
        var value = meizuFlags.getInt(attributes)
        value = if (isDark) value or bit else value and bit.inv()
        meizuFlags.setInt(attributes, value)
        activity.window.attributes = attributes
        true
    } catch (e: Exception) {
        false
    }
}