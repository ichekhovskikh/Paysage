package com.chekh.paysage.common.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.common.data.model.DesktopPageEntity

@Dao
abstract class DesktopPageDao {

    @Query("SELECT * FROM desktop_page")
    abstract fun getAll(): LiveData<List<DesktopPageEntity>>

    @Query("DELETE FROM desktop_page")
    abstract suspend fun removeAll()

    @Query("DELETE FROM desktop_page WHERE desktop_page.id NOT IN (SELECT pageId FROM desktop_widget)")
    abstract suspend fun removeEmptyPages()

    @Transaction
    open suspend fun add(page: DesktopPageEntity) {
        removeEmptyPages()
        val pages = getAsyncEqualsOrGreaterPages(page.position)
        pages.forEach { it.position++ }
        add(pages + page)
    }

    @Query("SELECT * FROM desktop_page WHERE desktop_page.position >= :position")
    protected abstract suspend fun getAsyncEqualsOrGreaterPages(position: Int): List<DesktopPageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun add(pages: List<DesktopPageEntity>)
}
