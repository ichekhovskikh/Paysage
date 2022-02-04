package com.chekh.paysage.feature.main.home.di

import com.chekh.paysage.feature.main.desktop.presentation.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.desktop.presentation.factory.DesktopWidgetModelFactoryImpl
import com.chekh.paysage.feature.main.desktop.presentation.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.desktop.presentation.tools.DesktopWidgetHostManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HomeActivityModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDesktopWidgetHostManager(
        desktopWidgetHostManager: DesktopWidgetHostManagerImpl
    ): DesktopWidgetHostManager

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDesktopWidgetModelFactory(
        desktopWidgetModelFactory: DesktopWidgetModelFactoryImpl
    ): DesktopWidgetModelFactory
}
