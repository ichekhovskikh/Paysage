package com.chekh.paysage.di.activity

import com.chekh.paysage.common.data.datasource.SettingsDataSource
import com.chekh.paysage.common.data.datasource.SettingsDataSourceImpl
import com.chekh.paysage.feature.main.common.data.datasource.*
import com.chekh.paysage.feature.widget.data.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataSourceModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindSettingsDataSource(
        settingsDataSource: SettingsDataSourceImpl
    ): SettingsDataSource

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAppDataSource(
        appDataSource: AppDataSourceImpl
    ): AppDataSource

    @Binds
    @ActivityRetainedScoped
    abstract fun bindCategoryDataSource(
        categoryDataSource: CategoryDataSourceImpl
    ): CategoryDataSource

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDockAppDataSource(
        dockAppDataSource: DockAppDataSourceImpl
    ): DockAppDataSource

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDesktopWidgetDataSource(
        widgetDataSource: DesktopWidgetDataSourceImpl
    ): DesktopWidgetDataSource

    @Binds
    @ActivityRetainedScoped
    abstract fun bindWidgetDataSource(
        widgetDataSource: WidgetDataSourceImpl
    ): WidgetDataSource

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDesktopPageDataSource(
        desktopPageDataSource: DesktopPageDataSourceImpl
    ): DesktopPageDataSource
}
