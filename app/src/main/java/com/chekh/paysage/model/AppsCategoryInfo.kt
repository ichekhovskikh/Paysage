package com.chekh.paysage.model

import android.graphics.Bitmap

data class AppsCategoryInfo(
    val id: Int,
    var title: CategoryTitle,
    var icon: Bitmap,
    var position: Int,
    var isHidden: Boolean
)