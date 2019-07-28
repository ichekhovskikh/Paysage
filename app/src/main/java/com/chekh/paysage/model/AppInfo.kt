package com.chekh.paysage.model

import android.content.Intent
import android.graphics.Bitmap

data class AppInfo(
    val id: Int,
    var title: String,
    var icon: Bitmap,
    var intent: Intent,
    var categoryId: Int,
    var position: Int,
    var isHidden: Boolean,
    var iconColor: IconColor? = null
)
