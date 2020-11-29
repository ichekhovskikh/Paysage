package com.chekh.paysage.feature.main.presentation.desktop.di

import com.chekh.paysage.feature.main.presentation.desktop.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.presentation.desktop.factory.DesktopWidgetModelFactoryImpl
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class DesktopModule {

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
