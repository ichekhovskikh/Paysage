package com.chekh.paysage.di.application

import android.content.Context
import android.content.SharedPreferences
import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ToolsModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext
        context: Context
    ): SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideDisplayMetrics(
        @ApplicationContext
        context: Context
    ): DisplayMetrics = context.resources.displayMetrics

    private companion object {
        const val SP_NAME = "application_prefs"
    }
}
