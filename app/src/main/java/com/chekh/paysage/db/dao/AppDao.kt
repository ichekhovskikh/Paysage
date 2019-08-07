package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.model.AppInfo

@Dao
interface AppDao {
    @Query("SELECT * FROM app WHERE packageName = :packageName")
    fun getByPackageName(packageName: String): AppInfo

    @Query("SELECT * FROM app")
    fun getAll(): List<AppInfo>

    @Query("SELECT * FROM app")
    fun getLiveAll(): LiveData<List<AppInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: AppInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(apps: List<AppInfo>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(app: AppInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(apps: List<AppInfo>)

    @Delete
    fun remove(app: AppInfo)

    @Query("DELETE FROM app")
    fun removeAll()
}