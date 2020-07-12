package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.feature.home.data.model.AppSettingsModel

@Dao
abstract class AppDao {

    @Query("SELECT * FROM app WHERE id = :packageName || :className")
    abstract fun getByUnique(packageName: String, className: String): AppSettingsModel

    @Query("SELECT * FROM app WHERE packageName = :packageName")
    abstract fun getByPackageName(packageName: String): List<AppSettingsModel>

    @Query("SELECT * FROM app")
    abstract fun getAll(): List<AppSettingsModel>

    @Query("SELECT * FROM app")
    abstract fun getAllLive(): LiveData<List<AppSettingsModel>>

    @Query("SELECT * FROM app WHERE app.id IN (SELECT appId FROM dock_app)")
    abstract fun getDockAppAllLive(): LiveData<List<AppSettingsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(app: AppSettingsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(apps: Set<AppSettingsModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(app: AppSettingsModel)

    @Delete
    abstract fun remove(app: AppSettingsModel)

    @Query("DELETE FROM app")
    abstract fun removeAll()

    @Query("DELETE FROM app WHERE packageName = :packageName")
    abstract fun removeByPackageName(packageName: String)

    @Transaction
    open fun updateAll(apps: Set<AppSettingsModel>) {
        removeAll()
        add(apps)
    }
}