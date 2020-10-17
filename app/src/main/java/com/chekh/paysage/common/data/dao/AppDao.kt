package com.chekh.paysage.common.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.common.data.model.AppSettingsEntity

@Dao
abstract class AppDao {

    @Query("SELECT * FROM app WHERE id = :packageName || :className")
    abstract fun getByUnique(packageName: String, className: String): AppSettingsEntity

    @Query("SELECT * FROM app WHERE packageName = :packageName")
    abstract fun getByPackageName(packageName: String): List<AppSettingsEntity>

    @Query("SELECT * FROM app")
    abstract fun getAll(): List<AppSettingsEntity>

    @Query("SELECT * FROM app")
    abstract fun getAllLive(): LiveData<List<AppSettingsEntity>>

    @Query("SELECT * FROM app WHERE app.dockPosition > -1 ORDER BY app.dockPosition")
    abstract fun getDockAppAllLive(): LiveData<List<AppSettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(app: AppSettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(apps: Set<AppSettingsEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(app: AppSettingsEntity)

    @Delete
    abstract fun remove(app: AppSettingsEntity)

    @Query("DELETE FROM app")
    abstract fun removeAll()

    @Query("DELETE FROM app WHERE packageName = :packageName")
    abstract fun removeByPackageName(packageName: String)

    @Transaction
    open fun updateAll(apps: Set<AppSettingsEntity>) {
        removeAll()
        add(apps)
    }
}
