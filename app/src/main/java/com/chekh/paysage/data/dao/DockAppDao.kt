package com.chekh.paysage.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.data.model.entity.DockAppSettingsEntity

@Dao
interface DockAppDao {
    @Query("SELECT * FROM dock_app")
    fun getAllLive(): LiveData<List<DockAppSettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(dockApp: DockAppSettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(dockApps: List<DockAppSettingsEntity>)

    @Delete
    fun remove(dockApp: DockAppSettingsEntity)
}