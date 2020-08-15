package com.chekh.paysage.feature.main.domain.model

import android.graphics.Bitmap
import com.chekh.paysage.data.model.IconColor

data class AppModel(
    val id: String,
    val packageName: String,
    val className: String,
    val title: String,
    val icon: Bitmap?,
    val categoryId: String,
    val position: Int,
    val isHidden: Boolean,
    val iconColor: IconColor
)
