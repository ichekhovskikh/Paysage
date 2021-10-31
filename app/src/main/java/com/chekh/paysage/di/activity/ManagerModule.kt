package com.chekh.paysage.di.activity

import android.app.Activity
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class ManagerModule {

    @Singleton
    @Provides
    fun provideWindowManager(activity: Activity): WindowManager = activity.windowManager
}
