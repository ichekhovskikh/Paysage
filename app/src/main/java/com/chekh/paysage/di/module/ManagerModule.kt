package com.chekh.paysage.di.module

import android.app.UiModeManager
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {

    @Singleton
    @Provides
    fun provideLauncherApps(context: Context): LauncherApps =
        context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    @Singleton
    @Provides
    fun providePackageManager(context: Context): PackageManager = context.packageManager

    @Singleton
    @Provides
    fun provideUiModeManager(context: Context): UiModeManager =
        context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
}