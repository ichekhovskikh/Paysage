package com.chekh.paysage.feature.main.presentation.desktop.tools

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetHostView
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.UserManager
import androidx.fragment.app.Fragment
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager.Companion.WIDGET_ATTACH_REQUEST_CODE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface DesktopWidgetHostManager {

    fun allocateDesktopWidgetId(): Int

    fun requestWidgetAttachPermissionsIfNeed(
        fragment: Fragment,
        widgetId: Int,
        packageName: String,
        className: String
    ): Boolean

    fun attach(
        widgetId: Int,
        packageName: String,
        className: String
    ): AppWidgetHostView?

    fun detach(widgetView: AppWidgetHostView)

    companion object {
        const val WIDGET_ATTACH_REQUEST_CODE = 1
    }
}

class DesktopWidgetHostManagerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val widgetManager: AppWidgetManager,
    private val userManager: UserManager,
    private val widgetHost: AppWidgetHost
) : DesktopWidgetHostManager {

    override fun allocateDesktopWidgetId() = widgetHost.allocateAppWidgetId()

    /**
     * @return has widget attach permissions
     */
    override fun requestWidgetAttachPermissionsIfNeed(
        fragment: Fragment,
        widgetId: Int,
        packageName: String,
        className: String
    ): Boolean {
        val widgetInfo = findWidget(packageName, className) ?: return false
        if (!widgetManager.bindAppWidgetIdIfAllowed(widgetId, widgetInfo.provider)) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_BIND).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, widgetInfo.provider)
            }
            fragment.startActivityForResult(intent, WIDGET_ATTACH_REQUEST_CODE)
            return false
        }
        return true
    }

    override fun attach(
        widgetId: Int,
        packageName: String,
        className: String
    ): AppWidgetHostView? {
        val widgetInfo = findWidget(packageName, className) ?: return null
        widgetManager.bindAppWidgetIdIfAllowed(widgetId, widgetInfo.provider)
        return widgetHost.createView(context, widgetId, widgetInfo)
    }

    override fun detach(widgetView: AppWidgetHostView) {
        widgetHost.deleteAppWidgetId(widgetView.appWidgetId)
    }

    private fun findWidget(packageName: String, className: String) =
        userManager.userProfiles
            .flatMap { widgetManager.getInstalledProvidersForProfile(it) }
            .find { it.provider.isSame(packageName, className) }

    private fun ComponentName.isSame(packageName: String, className: String) =
        this.packageName == packageName && this.className == className
}