package com.chekh.paysage.common.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "desktop_widget",
    indices = [Index(value = ["packageName", "className"], unique = true)]
)
data class DesktopWidgetSettingsEntity(
    @PrimaryKey
    var id: String = "",
    var packageName: String = "",
    var className: String = "",
    var label: String = "",
    var type: DesktopWidgetType = DesktopWidgetType.WIDGET,
    var x: Int = 0,
    var y: Int = 0,
    var height: Int = 0,
    var width: Int = 0,
    var minHeight: Int = 0,
    var minWidth: Int = 0,
    @Embedded(prefix = "style")
    var style: DesktopWidgetStyleSettingsEntity? = null
)

data class DesktopWidgetStyleSettingsEntity(
    var color: Int = 0,
    var alpha: Int = 0,
    var corner: Int = 0
)
