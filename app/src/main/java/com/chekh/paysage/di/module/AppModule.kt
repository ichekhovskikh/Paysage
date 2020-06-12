package com.chekh.paysage.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.di.tools.ViewModelFactory
import com.chekh.paysage.provider.ParamsProvider
import com.chekh.paysage.provider.ParamsProviderIml
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindApplication(application: PaysageApp): PaysageApp

    @Singleton
    @Binds
    abstract fun bindContext(application: PaysageApp): Context

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    abstract fun bindParamsProvider(paramsProvider: ParamsProviderIml): ParamsProvider
}
