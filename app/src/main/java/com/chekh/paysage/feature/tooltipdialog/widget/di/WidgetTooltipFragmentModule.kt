package com.chekh.paysage.feature.tooltipdialog.widget.di

import androidx.fragment.app.Fragment
import com.chekh.paysage.core.extension.getArgs
import com.chekh.paysage.feature.tooltipdialog.widget.presentation.WidgetTooltipFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class WidgetTooltipFragmentModule {

    @FragmentScoped
    @Provides
    fun provideArgs(fragment: Fragment): WidgetTooltipFragment.Args = fragment.getArgs()
}
