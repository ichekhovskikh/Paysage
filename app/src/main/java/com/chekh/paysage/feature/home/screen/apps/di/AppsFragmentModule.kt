package com.chekh.paysage.feature.home.screen.apps.di

import androidx.lifecycle.ViewModel
import com.chekh.paysage.di.tools.ViewModelKey
import com.chekh.paysage.feature.home.screen.apps.AppsViewModel
import com.chekh.paysage.feature.home.screen.apps.fragment.AppsFragment
import com.chekh.paysage.provider.ParamsProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class AppsFragmentModule {

    @AppsFragmentScope
    @Provides
    fun provideParams(fragment: AppsFragment, paramsProvider: ParamsProvider) =
        paramsProvider.provide(fragment)

    @AppsFragmentScope
    @Provides
    @IntoMap
    @ViewModelKey(AppsViewModel::class)
    fun provideViewModel(): ViewModel = AppsViewModel()

}
