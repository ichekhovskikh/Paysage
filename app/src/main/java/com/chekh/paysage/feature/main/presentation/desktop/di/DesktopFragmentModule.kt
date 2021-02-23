package com.chekh.paysage.feature.main.presentation.desktop.di

import androidx.fragment.app.Fragment
import com.chekh.paysage.core.extension.getParams
import com.chekh.paysage.feature.main.presentation.desktop.DesktopFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class DesktopFragmentModule {

    @FragmentScoped
    @Provides
    fun provideParams(fragment: Fragment): DesktopFragment.Params = fragment.getParams()
}
