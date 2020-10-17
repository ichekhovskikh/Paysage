package com.chekh.paysage.common.di

import com.chekh.paysage.PaysageApplication
import com.chekh.paysage.common.di.module.*
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
        ServiceModule::class,
        DatabaseModule::class,
        ManagerModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<PaysageApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: PaysageApplication): Builder

        fun build(): AppComponent
    }
}
