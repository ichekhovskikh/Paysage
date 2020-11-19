package com.chekh.paysage.feature.main.data.service

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import javax.inject.Inject

// TODO WIP
interface DesktopWidgetService {

    suspend fun startObserveWidgetEvents()

    suspend fun stopObserveWidgetEvents()

    suspend fun pullWidgets(packageName: String? = null)
}

class DesktopWidgetServiceImpl @Inject constructor(
    private val widgetManager: AppWidgetManager,
    private val widgetHost: AppWidgetHost
) : DesktopWidgetService {

    override suspend fun startObserveWidgetEvents() {
        widgetHost.startListening()
    }

    override suspend fun stopObserveWidgetEvents() {
        widgetHost.stopListening()
    }

    override suspend fun pullWidgets(packageName: String?) {
        // TODO update desktop widgets
        // val widgetId = widgetHost.allocateAppWidgetId()
        // widgetManager.bindAppWidgetIdIfAllowed(widgetId, it.provider)
    }
}
