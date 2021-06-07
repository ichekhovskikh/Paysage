package com.chekh.paysage.di.application

import android.app.UiModeManager
import android.app.WallpaperManager
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.UserManager
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
    fun provideUserManager(
        @ApplicationContext
        context: Context
    ): UserManager = context.getSystemService(Context.USER_SERVICE) as UserManager

    @Singleton
    @Provides
    fun provideWallpaperManager(
        @ApplicationContext
        context: Context
    ): WallpaperManager = WallpaperManager.getInstance(context)

    @Singleton
    @Provides
    fun provideAppWidgetManager(
        @ApplicationContext
        context: Context
    ): AppWidgetManager = AppWidgetManager.getInstance(context)

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
