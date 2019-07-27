package com.chekh.paysage.ui.statusbar

import android.app.Activity

class CommonStatusBarDecorator : StatusBarDecorator() {

    private val materialDecorator: StatusBarDecorator
    private val meizuDecorator: StatusBarDecorator
    private val xiaomiDecorator: StatusBarDecorator

    init {
        materialDecorator = MaterialStatusBarDecorator()
        meizuDecorator = MeizuStatusBarDecorator()
        xiaomiDecorator = XiaomiStatusBarDecorator(materialDecorator)
    }

    override fun statusBarDarkMode(activity: Activity, isDark: Boolean): Boolean {
        return when {
            meizuDecorator.statusBarDarkMode(activity, isDark) -> true
            xiaomiDecorator.statusBarDarkMode(activity, isDark) -> true
            else -> materialDecorator.statusBarDarkMode(activity, isDark)
        }
    }
}