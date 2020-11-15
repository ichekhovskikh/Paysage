package com.chekh.paysage.feature.widget.domain.gateway

import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import com.chekh.paysage.feature.widget.domain.model.WidgetAppModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.flow.Flow

interface WidgetGateway {

    fun getDesktopGridSpan(): Flow<Int>

    fun getBoardWidgets(): Flow<List<WidgetModel>>

    suspend fun getFirstAppForWidgetPackage(packageName: String): WidgetAppModel

    suspend fun pullBoardWidgets()

    suspend fun startObserveBoardWidgetUpdates(callback: AppsChangedCallback)

    suspend fun stopObserveBoardWidgetUpdates(callback: AppsChangedCallback)
}
