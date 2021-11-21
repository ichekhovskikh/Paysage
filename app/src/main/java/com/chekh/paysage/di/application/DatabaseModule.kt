package com.chekh.paysage.di.application

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.chekh.paysage.common.data.dao.*
import com.chekh.paysage.common.data.db.ApplicationDatabase
import com.chekh.paysage.common.data.db.Migrations
import com.chekh.paysage.common.data.db.PrepopulateDatabaseCallback
import com.chekh.paysage.core.provider.database.CategoriesProvider
import com.chekh.paysage.core.provider.database.PackagesProvider
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMigrations(): Array<Migration> = Migrations.get()

    @Singleton
    @Provides
    fun provideRoomDatabaseCallback(
        categoryDao: Lazy<CategoryDao>,
        packageDao: Lazy<PackageDao>,
        packagesProvider: PackagesProvider,
        categoriesProvider: CategoriesProvider
    ): RoomDatabase.Callback = PrepopulateDatabaseCallback(
        categoryDao,
        packageDao,
        packagesProvider,
        categoriesProvider
    )

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext
        context: Context,
        migrations: Array<Migration>,
        callback: RoomDatabase.Callback
    ) = Room.databaseBuilder(context, ApplicationDatabase::class.java, "debug_v1.db")
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
    fun provideDesktopWidgetDao(db: ApplicationDatabase): DesktopWidgetDao = db.desktopWidgetDao

    @Singleton
    @Provides
    fun provideDesktopPageDao(db: ApplicationDatabase): DesktopPageDao = db.desktopPageDao
}
