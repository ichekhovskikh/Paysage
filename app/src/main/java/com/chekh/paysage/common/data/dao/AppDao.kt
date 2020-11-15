package com.chekh.paysage.common.data.dao

import androidx.room.*
import com.chekh.paysage.common.data.model.AppSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AppDao {

    @Query("SELECT * FROM app WHERE packageName = :packageName LIMIT 1")
    abstract suspend fun getFirstByPackageName(packageName: String): AppSettingsEntity

    @Query("SELECT * FROM app")
    abstract suspend fun getAll(): List<AppSettingsEntity>

    @Query("SELECT * FROM app")
    abstract fun getAllFlow(): Flow<List<AppSettingsEntity>>

    @Query("SELECT * FROM app WHERE app.dockPosition > -1 ORDER BY app.dockPosition")
    abstract fun getDockAppAllFlow(): Flow<List<AppSettingsEntity>>

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
