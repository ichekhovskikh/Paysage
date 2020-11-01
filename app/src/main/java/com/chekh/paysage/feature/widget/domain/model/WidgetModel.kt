package com.chekh.paysage.feature.widget.domain.model

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

data class WidgetModel(
    val id: String,
    val packageName: String,
    val className: String,
    @DrawableRes
    val previewImageRes: Int,
    val label: String,
    val minHeight: Int,
    val minWidth: Int
) {
    var previewImage: Bitmap? = null
        private set

    constructor(
        id: String,
        packageName: String,
        className: String,
        @DrawableRes
        previewImageRes: Int,
        previewImage: Bitmap?,
        label: String,
        minHeight: Int,
        minWidth: Int
    ) : this(id, packageName, className, previewImageRes, label, minHeight, minWidth) {
        this.previewImage = previewImage
    }
}
