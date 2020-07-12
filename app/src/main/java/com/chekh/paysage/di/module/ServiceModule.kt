package com.chekh.paysage.di.module

import com.chekh.paysage.data.service.SettingsService
import com.chekh.paysage.data.service.SettingsServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ServiceModule {

    @Singleton
    @Binds
    abstract fun bindSettingsService(service: SettingsServiceImpl): SettingsService
}
