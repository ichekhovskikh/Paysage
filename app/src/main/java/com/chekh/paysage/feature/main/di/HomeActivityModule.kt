package com.chekh.paysage.feature.main.di

import androidx.lifecycle.ViewModel
import com.chekh.paysage.di.tools.ViewModelKey
import com.chekh.paysage.feature.main.data.factory.AppSettingsFactory
import com.chekh.paysage.feature.main.data.factory.AppSettingsFactoryImpl
import com.chekh.paysage.feature.main.screen.home.HomeFragment
import com.chekh.paysage.feature.main.screen.home.HomeViewModel
import com.chekh.paysage.feature.main.data.HomeGatewayImpl
import com.chekh.paysage.feature.main.data.service.*
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
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
    abstract fun bindHomeRepository(gateway: HomeGatewayImpl): HomeGateway

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

    @HomeActivityScope
    @Binds
    abstract fun bindDockAppService(
        dockAppService: DockAppServiceImpl
    ): DockAppService
}
