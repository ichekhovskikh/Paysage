package com.chekh.paysage.di.activity

import com.chekh.paysage.common.data.service.SettingsService
import com.chekh.paysage.common.data.service.SettingsServiceImpl
import com.chekh.paysage.feature.main.data.service.*
import com.chekh.paysage.feature.widget.data.service.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityComponent::class)
abstract class ServiceModule {

    @ExperimentalCoroutinesApi
    @Binds
    @ActivityScoped
    abstract fun bindSettingsProvider(
        service: SettingsServiceImpl
    ): SettingsService

    @ExperimentalCoroutinesApi
    @Binds
    @ActivityScoped
    abstract fun bindAppService(
        appService: AppServiceImpl
    ): AppService

    @Binds
    @ActivityScoped
    abstract fun bindCategoryService(
        categoryService: CategoryServiceImpl
    ): CategoryService

    @Binds
    @ActivityScoped
    abstract fun bindDockAppService(
        dockAppService: DockAppServiceImpl
    ): DockAppService

    @Binds
    @ActivityScoped
    abstract fun bindDesktopWidgetService(
        widgetService: DesktopWidgetServiceImpl
    ): DesktopWidgetService

    @ExperimentalCoroutinesApi
    @Binds
    @ActivityScoped
    abstract fun bindWidgetService(
        widgetService: WidgetServiceImpl
    ): WidgetService
}
