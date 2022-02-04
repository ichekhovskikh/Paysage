package com.chekh.paysage.di.activity

import android.appwidget.AppWidgetHost
import android.content.Context
import com.chekh.paysage.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class ToolsModule {

    @ActivityRetainedScoped
    @Provides
    fun provideAppWidgetHost(
        @ApplicationContext
        context: Context
    ): AppWidgetHost = AppWidgetHost(context, R.id.app_widget_host)
}
