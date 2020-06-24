package com.chekh.paysage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chekh.paysage.db.dao.AppDao
import com.chekh.paysage.db.dao.CategoryDao
import com.chekh.paysage.feature.home.data.model.AppSettingsModel
import com.chekh.paysage.feature.home.data.model.CategorySettingsModel
import com.chekh.paysage.db.Converters.BooleanTypeConverter
import com.chekh.paysage.db.Converters.IconColorTypeConverter
import com.chekh.paysage.db.Converters.AppCategoryTypeConverter
import com.chekh.paysage.db.Converters.DrawableTypeConverter
import com.chekh.paysage.db.dao.PackageDao
import com.chekh.paysage.feature.home.data.model.PackageSettingsModel

@Database(
    entities = [
        AppSettingsModel::class,
        CategorySettingsModel::class,
        PackageSettingsModel::class
    ],
    version = 1
)
@TypeConverters(
    BooleanTypeConverter::class,
    IconColorTypeConverter::class,
    AppCategoryTypeConverter::class,
    DrawableTypeConverter::class
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val appDao: AppDao
    abstract val categoryDao: CategoryDao
    abstract val packageDao: PackageDao
}
