package com.chekh.paysage.di.activity

import android.app.Activity
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
class ManagerModule {

    @Singleton
    @Provides
    fun provideWindowManager(activity: Activity): WindowManager = activity.windowManager
}
