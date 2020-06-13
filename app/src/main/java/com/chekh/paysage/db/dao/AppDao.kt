package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.feature.home.data.model.AppSettingsModel

@Dao
interface AppDao {
    @Query("SELECT * FROM app WHERE id = :packageName || :className")
    fun getByUnique(packageName: String, className: String): AppSettingsModel

    @Query("SELECT * FROM app WHERE packageName = :packageName")
    fun getByPackageName(packageName: String): List<AppSettingsModel>

    @Query("SELECT * FROM app")
    fun getAll(): List<AppSettingsModel>

    @Query("SELECT * FROM app")
    fun getLiveAll(): LiveData<List<AppSettingsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: AppSettingsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(apps: Set<AppSettingsModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(app: AppSettingsModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(apps: List<AppSettingsModel>)

    @Delete
    fun remove(app: AppSettingsModel)

    @Query("DELETE FROM app")
    fun removeAll()

    @Query("DELETE FROM app WHERE packageName = :packageName")
    fun removeByPackageName(packageName: String)
}