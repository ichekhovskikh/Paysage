package com.chekh.paysage.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.chekh.paysage.PaysageApplication
import com.chekh.paysage.di.tools.ViewModelFactory
import com.chekh.paysage.provider.*
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindContext(
        application: PaysageApplication
    ): Context

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Singleton
    @Binds
    abstract fun bindStatusBarDecorator(
        factory: CommonStatusBarDecorator
    ): StatusBarDecorator

    @Singleton
    @Binds
    abstract fun bindParamsProvider(
        paramsProvider: ParamsProviderIml
    ): ParamsProvider

    @Singleton
    @Binds
    abstract fun bindPackagesProvider(
        packagesProvider: PackagesProviderImpl
    ): PackagesProvider

    @Singleton
    @Binds
    abstract fun bindCategoriesProvider(
        categoriesProvider: CategoriesProviderImpl
    ): CategoriesProvider

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        dispatcherProvider: DispatcherProviderImpl
    ): DispatcherProvider
}
