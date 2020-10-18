package com.chekh.paysage.di.activity

import com.chekh.paysage.feature.main.data.factory.AppSettingsFactory
import com.chekh.paysage.feature.main.data.factory.AppSettingsFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class FactoryModule {

    @Binds
    @ActivityScoped
    abstract fun bindAppSettingsFactory(
        appSettingsFactory: AppSettingsFactoryImpl
    ): AppSettingsFactory
}
