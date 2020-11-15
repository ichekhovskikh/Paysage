package com.chekh.paysage.common.data.dao

import androidx.room.*
import com.chekh.paysage.common.data.model.CategorySettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllFlow(): Flow<List<CategorySettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(categories: List<CategorySettingsEntity>)
}
