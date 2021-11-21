package com.chekh.paysage.di.application

import com.chekh.paysage.core.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DecoratorModule {

    @Singleton
    @Binds
    abstract fun bindStatusBarDecorator(
        statusBarDecorator: CommonStatusBarDecorator
    ): StatusBarDecorator
}
