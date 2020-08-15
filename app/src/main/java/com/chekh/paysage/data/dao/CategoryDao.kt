package com.chekh.paysage.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.data.model.entity.CategorySettingsEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: String): CategorySettingsEntity

    @Query("SELECT * FROM category")
    fun getAll(): List<CategorySettingsEntity>

    @Query("SELECT * FROM category")
    fun getAllLive(): LiveData<List<CategorySettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(category: CategorySettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(categories: List<CategorySettingsEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: CategorySettingsEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categories: List<CategorySettingsEntity>)

    @Delete
    fun remove(category: CategorySettingsEntity)
}