package com.chekh.paysage.data.db

import dagger.Lazy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chekh.paysage.data.dao.CategoryDao
import com.chekh.paysage.data.dao.PackageDao
import com.chekh.paysage.provider.CategoriesProvider
import com.chekh.paysage.provider.DispatcherProvider
import com.chekh.paysage.provider.PackagesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PrepopulateDatabaseCallback(
    private val dispatcherProvider: DispatcherProvider,
    private val categoryDao: Lazy<CategoryDao>,
    private val packageDao: Lazy<PackageDao>,
    private val packagesProvider: PackagesProvider,
    private val categoriesProvider: CategoriesProvider
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.enableWriteAheadLogging()
        CoroutineScope(dispatcherProvider.io).launch {
            prepopulateCategories()
            prepopulatePackages()
        }
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