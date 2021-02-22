package com.chekh.paysage.common.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "desktop_page",
    indices = [Index(value = ["position"], unique = true)]
)
data class DesktopPageEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var position: Int = 0
)
