package com.chekh.paysage.di.module

import com.chekh.paysage.feature.main.MainActivity
import com.chekh.paysage.feature.main.di.HomeActivityModule
import com.chekh.paysage.feature.main.di.HomeActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @HomeActivityScope
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivity(): MainActivity
}
