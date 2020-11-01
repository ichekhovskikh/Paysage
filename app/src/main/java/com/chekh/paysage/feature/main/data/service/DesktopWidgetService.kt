package com.chekh.paysage.feature.main.data.service

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.content.pm.LauncherApps
import android.os.UserHandle
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import javax.inject.Inject

// TODO WIP
interface DesktopWidgetService {

    fun startObserveUpdates()

    fun stopObserveUpdates()
}

class DesktopWidgetServiceImpl @Inject constructor(
    private val launcherApps: LauncherApps,
    private val widgetManager: AppWidgetManager,
    private val widgetHost: AppWidgetHost
) : DesktopWidgetService, AppsChangedCallback() {

    override fun startObserveUpdates() {
        runCatching {
            widgetHost.startListening()
            launcherApps.registerCallback(this)
        }
    }

    override fun stopObserveUpdates() {
        runCatching {
            widgetHost.stopListening()
            launcherApps.unregisterCallback(this)
        }
    }

    override fun onAppsChanged(packageName: String, userHandle: UserHandle) {
        // TODO update desktop widgets
        // val widgetId = widgetHost.allocateAppWidgetId()
        // widgetManager.bindAppWidgetIdIfAllowed(widgetId, it.provider)
    }
}
