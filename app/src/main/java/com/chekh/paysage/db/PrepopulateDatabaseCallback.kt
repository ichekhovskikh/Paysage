package com.chekh.paysage.db

import dagger.Lazy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chekh.paysage.db.dao.CategoryDao
import com.chekh.paysage.db.dao.PackageDao
import com.chekh.paysage.provider.CategoriesProvider
import com.chekh.paysage.provider.PackagesProvider
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
        val categories = categoriesProvider.provide()
        categoryDao.get().add(categories)
    }

    private fun prepopulatePackages() {
        val packages = packagesProvider.provide()
        packageDao.get().add(packages)
    }
}