package com.chekh.paysage.feature.main.screen.desktop.di

import androidx.lifecycle.ViewModel
import com.chekh.paysage.common.di.tools.ViewModelKey
import com.chekh.paysage.feature.main.screen.desktop.DesktopViewModel
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
