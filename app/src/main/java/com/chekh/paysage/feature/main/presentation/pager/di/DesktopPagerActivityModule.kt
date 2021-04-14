package com.chekh.paysage.feature.main.presentation.pager.di

import com.chekh.paysage.feature.main.presentation.pager.factory.DesktopPageModelFactory
import com.chekh.paysage.feature.main.presentation.pager.factory.DesktopPageModelFactoryImpl
import com.chekh.paysage.feature.main.presentation.pager.tools.DesktopPagerSwitcherDragHandler
import com.chekh.paysage.feature.main.presentation.pager.tools.DesktopPagerSwitcherDragHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class DesktopPagerActivityModule {

    @Binds
    @ActivityScoped
    abstract fun bindDesktopPageModelFactory(
        desktopPageModelFactory: DesktopPageModelFactoryImpl
    ): DesktopPageModelFactory

    @Binds
    @ActivityScoped
    abstract fun bindDesktopPagerDragTouchHandler(
        desktopPagerSwitcherDragHandler: DesktopPagerSwitcherDragHandlerImpl
    ): DesktopPagerSwitcherDragHandler
}
