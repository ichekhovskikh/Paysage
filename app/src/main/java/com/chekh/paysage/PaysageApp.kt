package com.chekh.paysage

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import com.chekh.paysage.util.AppManager

class PaysageApp : Application() {

    private lateinit var uiManager: UiModeManager

    val nightMode get() = uiManager.nightMode

    override fun onCreate() {
        super.onCreate()
        launcher = this
        appManager = AppManager(launcher)
        uiManager = launcher.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    }

    companion object {
        lateinit var launcher: PaysageApp private set
        lateinit var appManager: AppManager private set
    }
}