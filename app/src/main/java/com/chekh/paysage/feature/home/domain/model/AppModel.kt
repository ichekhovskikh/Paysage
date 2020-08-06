package com.chekh.paysage.feature.home.domain.model

import android.graphics.Bitmap
import com.chekh.paysage.feature.home.data.model.IconColor

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
