package com.chekh.paysage.common.data.model

import android.graphics.Rect
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "desktop_widget")
data class DesktopWidgetSettingsEntity(
    @PrimaryKey
    var id: String = "",
    var packageName: String = "",
    var className: String = "",
    var label: String = "",
    var type: DesktopWidgetType = DesktopWidgetType.WIDGET,
    var bounds: Rect = Rect(),
    var minHeight: Int = 0,
    var minWidth: Int = 0,
    @Embedded(prefix = "style")
    var style: DesktopWidgetStyleSettingsEntity? = null
)

data class DesktopWidgetStyleSettingsEntity(
    var color: Int = 0,
    var alpha: Float = 0f,
    var corner: Int = 0,
    var elevation: Int = 0
)
