package com.chekh.paysage.common.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.common.data.model.DesktopWidgetSettingsEntity

@Dao
abstract class DesktopWidgetDao {

    @Query("SELECT * FROM desktop_widget WHERE desktop_widget.pageId = :pageId")
    abstract fun getByPage(pageId: Long): LiveData<List<DesktopWidgetSettingsEntity>>

    @Query("SELECT * FROM desktop_widget")
    abstract fun getAll(): LiveData<List<DesktopWidgetSettingsEntity>>

    @Query("SELECT * FROM desktop_widget")
    abstract suspend fun getAsyncAll(): List<DesktopWidgetSettingsEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(widget: DesktopWidgetSettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(widget: DesktopWidgetSettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(widgets: List<DesktopWidgetSettingsEntity>)

    @Query("DELETE FROM desktop_widget WHERE id = :id")
    abstract suspend fun removeById(id: String)

    @Query("DELETE FROM desktop_widget")
    abstract suspend fun removeAll()

    @Query("DELETE FROM desktop_widget WHERE desktop_widget.pageId = :pageId")
    abstract suspend fun removeByPage(pageId: Long)

    @Transaction
    open suspend fun updateAll(widgets: List<DesktopWidgetSettingsEntity>) {
        removeAll()
        add(widgets)
    }

    @Transaction
    open suspend fun updateByPage(pageId: Long, widgets: List<DesktopWidgetSettingsEntity>) {
        removeByPage(pageId)
        add(widgets)
    }
}
