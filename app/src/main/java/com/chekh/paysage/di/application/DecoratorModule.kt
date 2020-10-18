package com.chekh.paysage.di.application

import com.chekh.paysage.core.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DecoratorModule {

    @Singleton
    @Binds
    abstract fun bindStatusBarDecorator(
        statusBarDecorator: CommonStatusBarDecorator
    ): StatusBarDecorator
}
