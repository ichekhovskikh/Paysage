package com.chekh.paysage.feature.main.screen.apps.di

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.chekh.paysage.common.di.tools.ViewModelKey
import com.chekh.paysage.core.provider.ParamsProvider
import com.chekh.paysage.feature.main.domain.usecase.*
import com.chekh.paysage.feature.main.screen.apps.AppsFragment
import com.chekh.paysage.feature.main.screen.apps.AppsViewModel
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
        getAppsGroupByCategoriesScenario: GetAppsGroupByCategoriesScenario,
        getDockAppsWithSettingsScenario: GetDockAppsWithSettingsScenario
    ): ViewModel = AppsViewModel(
        getAppsGroupByCategoriesScenario,
        getDockAppsWithSettingsScenario
    )
}
