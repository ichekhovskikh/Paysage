package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.model.AppInfo

@Dao
interface AppDao {
    @Query("SELECT * FROM app WHERE id = :id")
    fun getById(id: Long): AppInfo

    @Query("SELECT * FROM app")
    fun getAll(): LiveData<List<AppInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: AppInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: List<AppInfo>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(app: AppInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(app: List<AppInfo>)

    @Delete
    fun remove(app: AppInfo)
}