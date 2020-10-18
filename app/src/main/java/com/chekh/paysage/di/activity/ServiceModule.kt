package com.chekh.paysage.di.activity

import com.chekh.paysage.common.data.service.SettingsService
import com.chekh.paysage.common.data.service.SettingsServiceImpl
import com.chekh.paysage.feature.main.data.service.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class ServiceModule {

    @Binds
    @ActivityScoped
    abstract fun bindSettingsService(
        service: SettingsServiceImpl
    ): SettingsService

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
}