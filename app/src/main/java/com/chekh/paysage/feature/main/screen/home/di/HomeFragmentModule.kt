package com.chekh.paysage.feature.main.screen.home.di

import com.chekh.paysage.feature.main.screen.apps.AppsFragment
import com.chekh.paysage.feature.main.screen.apps.di.AppsFragmentModule
import com.chekh.paysage.feature.main.screen.apps.di.AppsFragmentScope
import com.chekh.paysage.feature.main.screen.desktop.DesktopFragment
import com.chekh.paysage.feature.main.screen.desktop.di.DesktopFragmentModule
import com.chekh.paysage.feature.main.screen.desktop.di.DesktopFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {

    @AppsFragmentScope
    @ContributesAndroidInjector(modules = [AppsFragmentModule::class])
    abstract fun appsFragment(): AppsFragment

    @DesktopFragmentScope
    @ContributesAndroidInjector(modules = [DesktopFragmentModule::class])
    abstract fun desktopFragment(): DesktopFragment
}
