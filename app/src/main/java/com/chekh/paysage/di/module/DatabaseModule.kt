package com.chekh.paysage.di.module

import dagger.Lazy
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.chekh.paysage.data.db.ApplicationDatabase
import com.chekh.paysage.data.db.Migrations
import com.chekh.paysage.data.db.PrepopulateDatabaseCallback
import com.chekh.paysage.data.dao.AppDao
import com.chekh.paysage.data.dao.CategoryDao
import com.chekh.paysage.data.dao.DockAppDao
import com.chekh.paysage.data.dao.PackageDao
import com.chekh.paysage.provider.CategoriesProvider
import com.chekh.paysage.provider.DispatcherProvider
import com.chekh.paysage.provider.PackagesProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMigrations(): Array<Migration> = Migrations.get()

    @Singleton
    @Provides
    fun provideRoomDatabaseCallback(
        dispatcherProvider: DispatcherProvider,
        categoryDao: Lazy<CategoryDao>,
        packageDao: Lazy<PackageDao>,
        packagesProvider: PackagesProvider,
        categoriesProvider: CategoriesProvider
    ): RoomDatabase.Callback = PrepopulateDatabaseCallback(
        dispatcherProvider,
        categoryDao,
        packageDao,
        packagesProvider,
        categoriesProvider
    )

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context,
        migrations: Array<Migration>,
        callback: RoomDatabase.Callback
    ) = Room.databaseBuilder(context, ApplicationDatabase::class.java, "debug_v8.db")
        .addCallback(callback)
        .addMigrations(*migrations)
        .build()

    @Singleton
    @Provides
    fun provideAppDao(db: ApplicationDatabase): AppDao = db.appDao

    @Singleton
    @Provides
    fun provideCategoryDao(db: ApplicationDatabase): CategoryDao = db.categoryDao

    @Singleton
    @Provides
    fun providePackageDao(db: ApplicationDatabase): PackageDao = db.packageDao

    @Singleton
    @Provides
    fun provideDockAppDao(db: ApplicationDatabase): DockAppDao = db.dockAppDao
}