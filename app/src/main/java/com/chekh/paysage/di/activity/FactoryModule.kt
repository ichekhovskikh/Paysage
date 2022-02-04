package com.chekh.paysage.di.activity

import com.chekh.paysage.common.data.factory.AppSettingsFactory
import com.chekh.paysage.common.data.factory.AppSettingsFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FactoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAppSettingsFactory(
        appSettingsFactory: AppSettingsFactoryImpl
    ): AppSettingsFactory
}
