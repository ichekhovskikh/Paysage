package com.chekh.paysage.feature.home.screen.apps.di

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.chekh.paysage.di.tools.ViewModelKey
import com.chekh.paysage.feature.home.domain.usecase.AppsGroupByCategoriesUseCase
import com.chekh.paysage.feature.home.screen.apps.AppsViewModel
import com.chekh.paysage.feature.home.screen.apps.AppsFragment
import com.chekh.paysage.provider.ParamsProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class AppsFragmentModule {

    @AppsFragmentScope
    @Provides
    fun provideParams(fragment: AppsFragment, paramsProvider: ParamsProvider): Bundle =
        paramsProvider.provide(fragment)

    @AppsFragmentScope
    @Provides
    @IntoMap
    @ViewModelKey(AppsViewModel::class)
    fun provideViewModel(
        appsGroupByCategoriesUseCase: AppsGroupByCategoriesUseCase
    ): ViewModel = AppsViewModel(appsGroupByCategoriesUseCase)
}
