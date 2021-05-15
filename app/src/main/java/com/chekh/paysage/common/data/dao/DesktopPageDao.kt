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

    @Query("DELETE FROM desktop_page WHERE position = :position")
    abstract suspend fun removeByPosition(position: Int)

    @Transaction
    open suspend fun add(page: DesktopPageEntity) {
        val pages = getAsyncEqualsOrGreaterPages(page.position)
        pages.forEach { it.position++ }
        add(pages + page)
    }

    @Transaction
    open suspend fun removeByPositionWithShift(position: Int) {
        val pages = getAsyncGreaterPages(position)
        pages.forEach { it.position-- }
        removeByPosition(position)
        add(pages)
    }

    @Query("SELECT * FROM desktop_page WHERE desktop_page.position >= :position")
    protected abstract suspend fun getAsyncEqualsOrGreaterPages(position: Int): List<DesktopPageEntity>

    @Query("SELECT * FROM desktop_page WHERE desktop_page.position > :position")
    protected abstract suspend fun getAsyncGreaterPages(position: Int): List<DesktopPageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun add(pages: List<DesktopPageEntity>)
}
