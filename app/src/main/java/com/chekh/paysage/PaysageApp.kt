package com.chekh.paysage

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import com.chekh.paysage.db.PaysageDatabase
import com.chekh.paysage.manager.AppManager

class PaysageApp : Application() {

    override fun onCreate() {
        super.onCreate()
        launcher = this
        database = PaysageDatabase.instance
        appManager = AppManager(launcher)
        uiManager = launcher.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    }

    companion object {
        lateinit var launcher: PaysageApp private set
        lateinit var appManager: AppManager private set
        lateinit var uiManager: UiModeManager private set
        lateinit var database: PaysageDatabase private set
    }
}