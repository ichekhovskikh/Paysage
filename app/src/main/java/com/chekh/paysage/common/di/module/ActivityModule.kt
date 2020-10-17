package com.chekh.paysage.common.di.module

import com.chekh.paysage.feature.main.MainActivity
import com.chekh.paysage.feature.main.di.MainActivityModule
import com.chekh.paysage.feature.main.di.MainActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @MainActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun homeActivity(): MainActivity
}
