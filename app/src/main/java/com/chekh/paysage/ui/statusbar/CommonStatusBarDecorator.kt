package com.chekh.paysage.ui.statusbar

import android.app.Activity

class CommonStatusBarDecorator : StatusBarDecorator() {

    private val materialDecorator by lazy { MaterialStatusBarDecorator() }
    private val meizuDecorator by lazy { MeizuStatusBarDecorator() }
    private val xiaomiDecorator by lazy { XiaomiStatusBarDecorator(materialDecorator) }

    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        return when {
            meizuDecorator.statusBarDarkMode(activity, isDark) -> true
            xiaomiDecorator.statusBarDarkMode(activity, isDark) -> true
            else -> materialDecorator.statusBarDarkMode(activity, isDark)
        }
    }
}