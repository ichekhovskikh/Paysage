package com.chekh.paysage.di.application

import com.chekh.paysage.core.provider.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class ProviderModule {

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        dispatcherProvider: DispatcherProviderImpl
    ): DispatcherProvider

    @Binds
    abstract fun bindPackagesProvider(
        packagesProvider: PackagesProviderImpl
    ): PackagesProvider

    @Binds
    abstract fun bindCategoriesProvider(
        categoriesProvider: CategoriesProviderImpl
    ): CategoriesProvider
}
