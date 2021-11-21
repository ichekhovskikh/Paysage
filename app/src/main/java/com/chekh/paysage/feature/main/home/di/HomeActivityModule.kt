package com.chekh.paysage.feature.main.home.di

import com.chekh.paysage.feature.main.desktop.presentation.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.desktop.presentation.factory.DesktopWidgetModelFactoryImpl
import com.chekh.paysage.feature.main.desktop.presentation.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.desktop.presentation.tools.DesktopWidgetHostManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class HomeActivityModule {

    @Binds
    @ActivityScoped
    abstract fun bindDesktopWidgetHostManager(
        desktopWidgetHostManager: DesktopWidgetHostManagerImpl
    ): DesktopWidgetHostManager

    @Binds
    @ActivityScoped
    abstract fun bindDesktopWidgetModelFactory(
        desktopWidgetModelFactory: DesktopWidgetModelFactoryImpl
    ): DesktopWidgetModelFactory
}
