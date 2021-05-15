package com.chekh.paysage.feature.main.domain.gateway

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.ui.tools.Size
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.model.CategoryModel
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.tools.AppsChangedCallback

interface HomeGateway {
    suspend fun startObserveAppUpdates(callback: AppsChangedCallback)
    suspend fun stopObserveAppUpdates(callback: AppsChangedCallback)
    suspend fun startObserveWidgetEvents()
    suspend fun stopObserveWidgetEvents()
    suspend fun pullBoardApps(packageName: String?)
    suspend fun pullDesktopWidgets()
    suspend fun updateDesktopWidget(widget: DesktopWidgetModel)
    suspend fun updateDesktopWidgetsByPage(pageId: Long, widgets: List<DesktopWidgetModel>?)
    suspend fun removeDesktopWidget(widgetId: String)
    suspend fun addDesktopPage(page: DesktopPageModel)
    suspend fun removeDesktopPageByPosition(position: Int)
    fun getDockApps(): LiveData<List<AppModel>>
    fun getDockAppSettings(): LiveData<AppSettingsModel>
    fun getAppCategories(): LiveData<List<CategoryModel>>
    fun getBoardApps(): LiveData<List<AppModel>>
    fun getDesktopWidgetsByPage(pageId: Long): LiveData<List<DesktopWidgetModel>>
    fun getBoardAppSettings(): LiveData<AppSettingsModel>
    fun getDesktopGridSize(): LiveData<Size>
    fun getDesktopPages(): LiveData<List<DesktopPageModel>>
    fun getDesktopWidgets(): LiveData<List<DesktopWidgetModel>>
}
