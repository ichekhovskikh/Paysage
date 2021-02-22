package com.chekh.paysage.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chekh.paysage.common.data.dao.*
import com.chekh.paysage.common.data.db.Converters.AppCategoryTypeConverter
import com.chekh.paysage.common.data.db.Converters.BitmapTypeConverter
import com.chekh.paysage.common.data.db.Converters.BooleanTypeConverter
import com.chekh.paysage.common.data.db.Converters.IconColorTypeConverter
import com.chekh.paysage.common.data.db.Converters.DesktopWidgetTypeConverter
import com.chekh.paysage.common.data.db.Converters.RectTypeConverter
import com.chekh.paysage.common.data.model.*

@Database(
    entities = [
        AppSettingsEntity::class,
        DesktopPageEntity::class,
        DesktopWidgetSettingsEntity::class,
        CategorySettingsEntity::class,
        PackageSettingsEntity::class
    ],
    version = 1
)
@TypeConverters(
    BooleanTypeConverter::class,
    IconColorTypeConverter::class,
    AppCategoryTypeConverter::class,
    DesktopWidgetTypeConverter::class,
    BitmapTypeConverter::class,
    RectTypeConverter::class
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val appDao: AppDao
    abstract val categoryDao: CategoryDao
    abstract val packageDao: PackageDao
    abstract val desktopWidgetDao: DesktopWidgetDao
    abstract val desktopPageDao: DesktopPageDao
}
