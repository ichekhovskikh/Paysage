package com.chekh.paysage.common.di.module

import com.chekh.paysage.common.data.service.SettingsService
import com.chekh.paysage.common.data.service.SettingsServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ServiceModule {

    @Singleton
    @Binds
    abstract fun bindSettingsService(service: SettingsServiceImpl): SettingsService
}
