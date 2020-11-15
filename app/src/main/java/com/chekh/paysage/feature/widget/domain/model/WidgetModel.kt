package com.chekh.paysage.feature.widget.domain.model

import android.graphics.Bitmap

data class WidgetModel(
    val id: String,
    val packageName: String,
    val className: String,
    val previewImageId: Int,
    val previewImage: Bitmap?,
    val label: String,
    val minHeight: Int,
    val minWidth: Int,
    val minRows: Int = 1,
    val minColumns: Int = 1
) {

    override fun hashCode() = id.toInt()

    override fun equals(other: Any?): Boolean {
        val it = other as? WidgetModel ?: return false
        return it.id == id && it.packageName == packageName &&
            it.className == className && it.previewImageId == it.previewImageId &&
            it.label == it.label && it.minHeight == minHeight && it.minWidth == minWidth &&
            it.minRows == minRows && it.minColumns == minColumns
    }
}
