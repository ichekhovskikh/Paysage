package com.chekh.paysage.di.application

import com.chekh.paysage.core.provider.database.CategoriesProvider
import com.chekh.paysage.core.provider.database.CategoriesProviderImpl
import com.chekh.paysage.core.provider.database.PackagesProvider
import com.chekh.paysage.core.provider.database.PackagesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    abstract fun bindPackagesProvider(
        packagesProvider: PackagesProviderImpl
    ): PackagesProvider

    @Binds
    abstract fun bindCategoriesProvider(
        categoriesProvider: CategoriesProviderImpl
    ): CategoriesProvider
}
