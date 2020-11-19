package com.chekh.paysage.common.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.common.data.model.AppSettingsEntity

@Dao
abstract class AppDao {

    @Query("SELECT * FROM app WHERE packageName = :packageName LIMIT 1")
    abstract fun getFirstByPackageName(packageName: String): LiveData<AppSettingsEntity>

    @Query("SELECT * FROM app")
    abstract suspend fun getAsyncAll(): List<AppSettingsEntity>

    @Query("SELECT * FROM app")
    abstract fun getAll(): LiveData<List<AppSettingsEntity>>

    @Query("SELECT * FROM app WHERE app.dockPosition > -1 ORDER BY app.dockPosition")
    abstract fun getDockAppAll(): LiveData<List<AppSettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(apps: Set<AppSettingsEntity>)

    @Query("DELETE FROM app")
    abstract suspend fun removeAll()

    @Query("DELETE FROM app WHERE packageName = :packageName")
    abstract suspend fun removeByPackageName(packageName: String)

    @Transaction
    open suspend fun updateAll(apps: Set<AppSettingsEntity>) {
        removeAll()
        add(apps)
    }
}
