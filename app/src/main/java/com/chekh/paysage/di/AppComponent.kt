package com.chekh.paysage.di

import com.chekh.paysage.PaysageApp
import com.chekh.paysage.di.module.ActivityModule
import com.chekh.paysage.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<PaysageApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: PaysageApp): Builder

        fun build(): AppComponent
    }
}
