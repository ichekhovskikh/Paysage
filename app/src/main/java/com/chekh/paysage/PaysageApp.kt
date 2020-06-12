package com.chekh.paysage

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import com.chekh.paysage.db.PaysageDatabase
import com.chekh.paysage.di.AppComponent
import com.chekh.paysage.di.DaggerAppComponent
import com.chekh.paysage.manager.AppManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class PaysageApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()

        appComponent.inject(this)

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
        lateinit var appComponent: AppComponent private set
    }
}