package com.chekh.paysage.feature.widget.domain.gateway

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.widget.domain.model.WidgetAppModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel

interface WidgetGateway {
    fun getBoardWidgets(): LiveData<List<WidgetModel>>
    fun getFirstWidgetAppByPackage(packageName: String): LiveData<WidgetAppModel>
}
