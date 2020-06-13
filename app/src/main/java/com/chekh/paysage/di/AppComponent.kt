package com.chekh.paysage.di

import com.chekh.paysage.PaysageApplication
import com.chekh.paysage.di.module.ActivityModule
import com.chekh.paysage.di.module.AppModule
import com.chekh.paysage.di.module.DatabaseModule
import com.chekh.paysage.di.module.ManagerModule
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
