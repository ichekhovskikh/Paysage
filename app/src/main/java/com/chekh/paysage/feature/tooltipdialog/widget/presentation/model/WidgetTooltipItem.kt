package com.chekh.paysage.feature.tooltipdialog.widget.presentation.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.chekh.paysage.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class WidgetTooltipItem(@DrawableRes val icon: Int, @StringRes val label: Int): Parcelable {
    EDIT(R.drawable.ic_tooltip_edit, R.string.widget_tooltip_edit),
    CROP(R.drawable.ic_tooltip_crop, R.string.widget_tooltip_crop),
    REMOVE(R.drawable.ic_tooltip_remove, R.string.widget_tooltip_remove),
    INFO(R.drawable.ic_tooltip_info, R.string.widget_tooltip_info)
}
