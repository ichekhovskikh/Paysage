package com.chekh.paysage.di.activity

import com.chekh.paysage.common.data.datasource.SettingsDataSource
import com.chekh.paysage.common.data.datasource.SettingsDataSourceImpl
import com.chekh.paysage.feature.main.common.data.datasource.*
import com.chekh.paysage.feature.widget.data.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class DataSourceModule {

    @Binds
    @ActivityScoped
    abstract fun bindSettingsDataSource(
        settingsDataSource: SettingsDataSourceImpl
    ): SettingsDataSource

    @Binds
    @ActivityScoped
    abstract fun bindAppDataSource(
        appDataSource: AppDataSourceImpl
    ): AppDataSource

    @Binds
    @ActivityScoped
    abstract fun bindCategoryDataSource(
        categoryDataSource: CategoryDataSourceImpl
    ): CategoryDataSource

    @Binds
    @ActivityScoped
    abstract fun bindDockAppDataSource(
        dockAppDataSource: DockAppDataSourceImpl
    ): DockAppDataSource

    @Binds
    @ActivityScoped
    abstract fun bindDesktopWidgetDataSource(
        widgetDataSource: DesktopWidgetDataSourceImpl
    ): DesktopWidgetDataSource

    @Binds
    @ActivityScoped
    abstract fun bindWidgetDataSource(
        widgetDataSource: WidgetDataSourceImpl
    ): WidgetDataSource

    @Binds
    @ActivityScoped
    abstract fun bindDesktopPageDataSource(
        desktopPageDataSource: DesktopPageDataSourceImpl
    ): DesktopPageDataSource
}
