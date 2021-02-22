package com.chekh.paysage.common.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.common.data.model.DesktopPageEntity

@Dao
interface DesktopPageDao {

    @Query("SELECT * FROM desktop_page")
    fun getAll(): LiveData<List<DesktopPageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(page: DesktopPageEntity)

    @Query("DELETE FROM desktop_page WHERE id = :id")
    suspend fun removeById(id: Long)
}
