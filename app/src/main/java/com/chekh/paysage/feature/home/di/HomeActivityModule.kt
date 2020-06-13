package com.chekh.paysage.feature.home.di

import androidx.lifecycle.ViewModel
import com.chekh.paysage.di.tools.ViewModelKey
import com.chekh.paysage.feature.home.data.factory.AppSettingsFactory
import com.chekh.paysage.feature.home.data.factory.AppSettingsFactoryImpl
import com.chekh.paysage.feature.home.HomeFragment
import com.chekh.paysage.feature.home.HomeViewModel
import com.chekh.paysage.feature.home.data.HomeRepositoryImpl
import com.chekh.paysage.feature.home.data.service.AppService
import com.chekh.paysage.feature.home.data.service.AppServiceImpl
import com.chekh.paysage.feature.home.data.service.CategoryService
import com.chekh.paysage.feature.home.data.service.CategoryServiceImpl
import com.chekh.paysage.feature.home.domain.HomeRepository
import com.chekh.paysage.feature.home.domain.gateway.AppsGroupByCategoriesGateway
import com.chekh.paysage.feature.home.domain.gateway.StartObserveUpdatesGateway
import com.chekh.paysage.feature.home.domain.gateway.StopObserveUpdatesGateway
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeActivityModule {

    @HomeFragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun homeFragment(): HomeFragment

    @HomeActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @HomeActivityScope
    @Binds
    abstract fun bindHomeRepository(repository: HomeRepositoryImpl): HomeRepository

    @HomeActivityScope
    @Binds
    abstract fun bindAppsGroupByCategoriesGateway(
        repository: HomeRepository
    ): AppsGroupByCategoriesGateway

    @HomeActivityScope
    @Binds
    abstract fun bindStartObserveUpdatesGateway(
        repository: HomeRepository
    ): StartObserveUpdatesGateway

    @HomeActivityScope
    @Binds
    abstract fun bindStopObserveUpdatesGateway(
        repository: HomeRepository
    ): StopObserveUpdatesGateway

    @HomeActivityScope
    @Binds
    abstract fun bindAppSettingsFactory(
        appSettingsFactory: AppSettingsFactoryImpl
    ): AppSettingsFactory

    @HomeActivityScope
    @Binds
    abstract fun bindAppService(
        appService: AppServiceImpl
    ): AppService

    @HomeActivityScope
    @Binds
    abstract fun bindCategoryService(
        categoryService: CategoryServiceImpl
    ): CategoryService
}
