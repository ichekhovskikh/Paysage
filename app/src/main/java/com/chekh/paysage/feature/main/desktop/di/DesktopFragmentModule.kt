package com.chekh.paysage.feature.main.desktop.di

import androidx.fragment.app.Fragment
import com.chekh.paysage.core.extension.getArgs
import com.chekh.paysage.feature.main.desktop.presentation.DesktopFragment
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
    fun provideArgs(fragment: Fragment): DesktopFragment.Args = fragment.getArgs()
}
