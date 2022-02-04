package com.chekh.paysage.feature.main.pager.di

import com.chekh.paysage.feature.main.pager.presentation.factory.DesktopPageModelFactory
import com.chekh.paysage.feature.main.pager.presentation.factory.DesktopPageModelFactoryImpl
import com.chekh.paysage.feature.main.pager.presentation.tools.DesktopPagerSwitcherDragHandler
import com.chekh.paysage.feature.main.pager.presentation.tools.DesktopPagerSwitcherDragHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DesktopPagerActivityModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDesktopPageModelFactory(
        desktopPageModelFactory: DesktopPageModelFactoryImpl
    ): DesktopPageModelFactory

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDesktopPagerDragTouchHandler(
        desktopPagerSwitcherDragHandler: DesktopPagerSwitcherDragHandlerImpl
    ): DesktopPagerSwitcherDragHandler
}
