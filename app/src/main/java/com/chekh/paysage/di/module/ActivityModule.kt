package com.chekh.paysage.di.module

import com.chekh.paysage.feature.home.HomeActivity
import com.chekh.paysage.feature.home.di.HomeActivityModule
import com.chekh.paysage.feature.home.di.HomeActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @HomeActivityScope
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivity(): HomeActivity
}
