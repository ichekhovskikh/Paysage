package com.chekh.paysage.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chekh.paysage.data.dao.AppDao
import com.chekh.paysage.data.dao.CategoryDao
import com.chekh.paysage.data.model.entity.AppSettingsEntity
import com.chekh.paysage.data.model.entity.CategorySettingsEntity
import com.chekh.paysage.data.db.Converters.BooleanTypeConverter
import com.chekh.paysage.data.db.Converters.IconColorTypeConverter
import com.chekh.paysage.data.db.Converters.AppCategoryTypeConverter
import com.chekh.paysage.data.db.Converters.BitmapTypeConverter
import com.chekh.paysage.data.dao.DockAppDao
import com.chekh.paysage.data.dao.PackageDao
import com.chekh.paysage.data.model.entity.DockAppSettingsEntity
import com.chekh.paysage.data.model.entity.PackageSettingsEntity

@Database(
    entities = [
        AppSettingsEntity::class,
        DockAppSettingsEntity::class,
        CategorySettingsEntity::class,
        PackageSettingsEntity::class
    ],
    version = 1
)
@TypeConverters(
    BooleanTypeConverter::class,
    IconColorTypeConverter::class,
    AppCategoryTypeConverter::class,
    BitmapTypeConverter::class
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val appDao: AppDao
    abstract val dockAppDao: DockAppDao
    abstract val categoryDao: CategoryDao
    abstract val packageDao: PackageDao
}
