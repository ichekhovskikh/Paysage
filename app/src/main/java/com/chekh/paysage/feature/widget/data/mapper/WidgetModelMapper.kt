package com.chekh.paysage.feature.widget.data.mapper

import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.core.provider.SettingsProvider
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

class WidgetModelMapper @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val packageManager: PackageManager,
    private val settingsProvider: SettingsProvider
) : OneParameterMapper<AppWidgetProviderInfo, WidgetModel> {

    override fun map(source: AppWidgetProviderInfo) = source.run {
        val densityDpi = context.resources.displayMetrics.densityDpi
        val spanCount = settingsProvider.desktopGridSpan
        val spanWidth = densityDpi.toFloat() / spanCount

        var previewImageBitmap = loadPreviewImage(context, densityDpi)?.toBitmap()
        val previewImageRes = if (previewImageBitmap == null) previewImage else icon
        previewImageBitmap = previewImageBitmap ?: loadIcon(context, densityDpi)?.toBitmap()
        val minColumns = min(ceil(minWidth.toFloat() / spanWidth).toInt(), spanCount)
        val minRows = ceil(minHeight.toFloat() / minWidth * minColumns).toInt()

        WidgetModel(
            id = provider.packageName + provider.className,
            packageName = provider.packageName,
            className = provider.className,
            previewImageRes = previewImageRes,
            previewImage = previewImageBitmap,
            label = loadLabel(packageManager),
            minHeight = minRows,
            minWidth = minColumns
        )
    }
}
