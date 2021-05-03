package com.chekh.paysage.common.data.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chekh.paysage.common.data.dao.CategoryDao
import com.chekh.paysage.common.data.dao.PackageDao
import com.chekh.paysage.core.provider.database.CategoriesProvider
import com.chekh.paysage.core.provider.database.PackagesProvider
import com.chekh.paysage.core.provider.io
import dagger.Lazy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrepopulateDatabaseCallback(
    private val categoryDao: Lazy<CategoryDao>,
    private val packageDao: Lazy<PackageDao>,
    private val packagesProvider: PackagesProvider,
    private val categoriesProvider: CategoriesProvider
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.enableWriteAheadLogging()
        GlobalScope.launch(io) {
            prepopulateCategories()
            prepopulatePackages()
        }
    }

    private suspend fun prepopulateCategories() {
        val categories = categoriesProvider.provide()
        categoryDao.get().add(categories)
    }

    private suspend fun prepopulatePackages() {
        val packages = packagesProvider.provide()
        packageDao.get().add(packages)
    }
}
