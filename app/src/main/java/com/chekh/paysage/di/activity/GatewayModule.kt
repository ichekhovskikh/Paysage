package com.chekh.paysage.di.activity

import com.chekh.paysage.feature.main.data.HomeGatewayImpl
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class GatewayModule {

    @Binds
    @ActivityScoped
    abstract fun bindHomeGateway(gateway: HomeGatewayImpl): HomeGateway
}
