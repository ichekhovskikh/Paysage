package com.chekh.paysage.feature.home.screen.desktop.di

import androidx.lifecycle.ViewModel
import com.chekh.paysage.di.tools.ViewModelKey
import com.chekh.paysage.feature.home.screen.desktop.DesktopViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class DesktopFragmentModule {

    @DesktopFragmentScope
    @Provides
    @IntoMap
    @ViewModelKey(DesktopViewModel::class)
    fun provideViewModel(): ViewModel = DesktopViewModel()
}
