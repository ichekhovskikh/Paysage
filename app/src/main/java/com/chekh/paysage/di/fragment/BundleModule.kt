package com.chekh.paysage.di.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chekh.paysage.core.provider.ParamsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class BundleModule {

    @FragmentScoped
    @Provides
    fun provideParams(fragment: Fragment, paramsProvider: ParamsProvider): Bundle =
        paramsProvider.provide(fragment)
}
