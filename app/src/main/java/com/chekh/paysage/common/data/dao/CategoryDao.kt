package com.chekh.paysage.common.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.common.data.model.CategorySettingsEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAll(): LiveData<List<CategorySettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(categories: List<CategorySettingsEntity>)
}
