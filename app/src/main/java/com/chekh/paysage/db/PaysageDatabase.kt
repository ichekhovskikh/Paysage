package com.chekh.paysage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chekh.paysage.PaysageApp.Companion.launcher
import com.chekh.paysage.db.dao.AppDao
import com.chekh.paysage.db.dao.CategoryDao
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.db.Convectors.BooleanTypeConverter
import com.chekh.paysage.db.Convectors.IconColorTypeConverter
import com.chekh.paysage.db.Convectors.CategoryTitleTypeConverter
import com.chekh.paysage.db.Convectors.BitmapTypeConverter
import com.chekh.paysage.db.dao.PackageDao
import com.chekh.paysage.model.PackageInfo
import com.chekh.paysage.provider.DefaultCategoriesProvider
import com.chekh.paysage.provider.DefaultPackagesProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [AppInfo::class, CategoryInfo::class, PackageInfo::class], version = 3)
@TypeConverters(
    BooleanTypeConverter::class,
    IconColorTypeConverter::class,
    CategoryTitleTypeConverter::class,
    BitmapTypeConverter::class
)
abstract class PaysageDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
    abstract fun categoryDao(): CategoryDao
    abstract fun packageDao(): PackageDao

    companion object {
        val instance: PaysageDatabase by lazy { createInstance(launcher) }

        private fun createInstance(context: Context) =
            Room.databaseBuilder(context, PaysageDatabase::class.java, "paysageDebug.db")
                .addCallback(prepopulateDatabaseCallback)
                .addMigrations(*Migrations.get())
                .build()

        private val prepopulateDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.enableWal()
                GlobalScope.launch {
                    prepopulateCategories()
                    prepopulatePackages()
                }
            }

            private fun SupportSQLiteDatabase.enableWal() {
                enableWriteAheadLogging()
            }

            private fun prepopulateCategories() {
                val categories = DefaultCategoriesProvider.getDefaultCategories()
                instance.categoryDao().add(categories)
            }

            private fun prepopulatePackages() {
                val packages = DefaultPackagesProvider.getDefaultPackages()
                instance.packageDao().add(packages)
            }
        }
    }
}
