package com.chekh.paysage.common.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.common.data.model.DesktopPageEntity

@Dao
abstract class DesktopPageDao {

    @Query("SELECT * FROM desktop_page")
    abstract fun getAll(): LiveData<List<DesktopPageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(page: DesktopPageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(pages: List<DesktopPageEntity>)

    @Query("DELETE FROM desktop_page")
    abstract suspend fun removeAll()

    @Query("SELECT * FROM desktop_page WHERE desktop_page.id IN (SELECT pageId FROM desktop_widget)")
    abstract suspend fun getAsyncNotEmptyPages(): List<DesktopPageEntity>

    suspend fun removeEmptyPages() {
        val pages = getAsyncNotEmptyPages().mapIndexed { index, page ->
            page.apply { position = index }
        }
        removeAll()
        add(pages)
    }
}
