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
import com.chekh.paysage.model.AppsCategoryInfo
import com.chekh.paysage.db.Convectors.BooleanTypeConverter
import com.chekh.paysage.db.Convectors.IconColorTypeConverter
import com.chekh.paysage.db.Convectors.CategoryTitleTypeConverter
import com.chekh.paysage.db.Convectors.BitmapTypeConverter
import com.chekh.paysage.model.CategoryTitle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [AppInfo::class, AppsCategoryInfo::class], version = 1)
@TypeConverters(
    BooleanTypeConverter::class,
    IconColorTypeConverter::class,
    CategoryTitleTypeConverter::class,
    BitmapTypeConverter::class
)
abstract class PaysageDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        val instance: PaysageDatabase by lazy { createInstance(launcher) }

        private fun createInstance(context: Context) =
            Room.databaseBuilder(context, PaysageDatabase::class.java, "paysage.db")
                .addCallback(prepopulateDatabaseCallback)
                .addMigrations(*Migrations.get())
                .build()

        private val prepopulateDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {
                    var position = 0
                    val categories = CategoryTitle.values().map { AppsCategoryInfo(it.id, it, position++, false) }
                    instance.categoryDao().add(categories)
                }
            }
        }
    }
}
