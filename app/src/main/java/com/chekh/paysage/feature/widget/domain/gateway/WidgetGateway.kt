package com.chekh.paysage.feature.widget.domain.gateway

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.ui.tools.Size
import com.chekh.paysage.feature.widget.domain.model.AppForWidgetModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel

interface WidgetGateway {
    fun getBoardWidgets(): LiveData<List<WidgetModel>>
    fun getFirstAppForWidgetPackage(packageName: String): LiveData<AppForWidgetModel>
    fun getDesktopGridSize(): LiveData<Size>
}
