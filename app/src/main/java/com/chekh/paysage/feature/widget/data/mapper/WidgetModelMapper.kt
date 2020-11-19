package com.chekh.paysage.feature.widget.data.mapper

import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WidgetModelMapper @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val packageManager: PackageManager
) : OneParameterMapper<AppWidgetProviderInfo, WidgetModel> {

    override fun map(source: AppWidgetProviderInfo) = source.run {
        var previewImageBitmap = loadPreviewImage(context, 0)?.toBitmap()
        val previewImageId = if (previewImageBitmap == null) previewImage else icon
        previewImageBitmap = previewImageBitmap ?: loadIcon(context, 0)?.toBitmap()

        WidgetModel(
            id = provider.packageName + provider.className,
            packageName = provider.packageName,
            className = provider.className,
            previewImageId = previewImageId,
            previewImage = previewImageBitmap,
            label = loadLabel(packageManager),
            minHeight = minHeight,
            minWidth = minWidth
        )
    }
}
