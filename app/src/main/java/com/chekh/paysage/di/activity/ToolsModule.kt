package com.chekh.paysage.di.activity

import android.appwidget.AppWidgetHost
import android.content.Context
import com.chekh.paysage.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ToolsModule {

    @ActivityScoped
    @Provides
    fun provideAppWidgetHost(
        @ActivityContext
        context: Context
    ): AppWidgetHost = AppWidgetHost(context, R.id.app_widget_host)
}
