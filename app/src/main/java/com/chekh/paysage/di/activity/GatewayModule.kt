package com.chekh.paysage.di.activity

import com.chekh.paysage.feature.main.common.data.HomeGatewayImpl
import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import com.chekh.paysage.feature.widget.data.WidgetGatewayImpl
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GatewayModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindHomeGateway(gateway: HomeGatewayImpl): HomeGateway

    @Binds
    @ActivityRetainedScoped
    abstract fun bindWidgetGateway(gateway: WidgetGatewayImpl): WidgetGateway
}
