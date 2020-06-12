package com.chekh.paysage.feature.home.di

import com.chekh.paysage.feature.home.screen.apps.di.AppsFragmentModule
import com.chekh.paysage.feature.home.screen.apps.di.AppsFragmentScope
import com.chekh.paysage.feature.home.screen.apps.fragment.AppsFragment
import com.chekh.paysage.feature.home.screen.desktop.di.DesktopFragmentModule
import com.chekh.paysage.feature.home.screen.desktop.fragment.DesktopFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {

    @AppsFragmentScope
    @ContributesAndroidInjector(modules = [AppsFragmentModule::class])
    abstract fun appsFragment(): AppsFragment

    @AppsFragmentScope
    @ContributesAndroidInjector(modules = [DesktopFragmentModule::class])
    abstract fun desktopFragment(): DesktopFragment

}
