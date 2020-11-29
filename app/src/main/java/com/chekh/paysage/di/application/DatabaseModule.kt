package com.chekh.paysage.di.application

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.common.data.dao.CategoryDao
import com.chekh.paysage.common.data.dao.DesktopWidgetDao
import com.chekh.paysage.common.data.dao.PackageDao
import com.chekh.paysage.common.data.db.ApplicationDatabase
import com.chekh.paysage.common.data.db.Migrations
import com.chekh.paysage.common.data.db.PrepopulateDatabaseCallback
import com.chekh.paysage.core.provider.CategoriesProvider
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.provider.PackagesProvider
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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
}
