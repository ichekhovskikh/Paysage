package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.feature.home.data.model.DockAppSettingsModel

@Dao
interface DockAppDao {
    @Query("SELECT * FROM dock_app")
    fun getAllLive(): LiveData<List<DockAppSettingsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(dockApp: DockAppSettingsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(dockApps: List<DockAppSettingsModel>)

    @Delete
    fun remove(dockApp: DockAppSettingsModel)
}