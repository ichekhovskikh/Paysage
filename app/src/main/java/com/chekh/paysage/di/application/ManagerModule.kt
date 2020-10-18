package com.chekh.paysage.di.application

import android.app.UiModeManager
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ManagerModule {

    @Singleton
    @Provides
    fun provideLauncherApps(
        @ApplicationContext
        context: Context
    ): LauncherApps = context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    @Singleton
    @Provides
    fun providePackageManager(
        @ApplicationContext
        context: Context
    ): PackageManager = context.packageManager

    @Singleton
    @Provides
    fun provideUiModeManager(
        @ApplicationContext
        context: Context
    ): UiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
}
