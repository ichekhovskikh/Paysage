package com.chekh.paysage.common.data.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chekh.paysage.common.data.dao.CategoryDao
import com.chekh.paysage.common.data.dao.PackageDao
import com.chekh.paysage.core.provider.CategoriesProvider
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.provider.PackagesProvider
import dagger.Lazy
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
