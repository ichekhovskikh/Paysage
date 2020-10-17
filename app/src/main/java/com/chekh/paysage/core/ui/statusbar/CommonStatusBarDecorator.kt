package com.chekh.paysage.core.ui.statusbar

import android.app.Activity
import javax.inject.Inject

class CommonStatusBarDecorator @Inject constructor(
    private val materialDecorator: MaterialStatusBarDecorator,
    private val meizuDecorator: MeizuStatusBarDecorator,
    private val xiaomiDecorator: XiaomiStatusBarDecorator
) : StatusBarDecorator() {

    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        return when {
            meizuDecorator.statusBarDarkMode(activity, isDark) -> true
            xiaomiDecorator.statusBarDarkMode(activity, isDark) -> true
            else -> materialDecorator.statusBarDarkMode(activity, isDark)
        }
    }
}
