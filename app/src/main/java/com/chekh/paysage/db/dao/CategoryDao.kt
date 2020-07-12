package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.feature.home.data.model.CategorySettingsModel

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: String): CategorySettingsModel

    @Query("SELECT * FROM category")
    fun getAll(): List<CategorySettingsModel>

    @Query("SELECT * FROM category")
    fun getAllLive(): LiveData<List<CategorySettingsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(category: CategorySettingsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(categories: List<CategorySettingsModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: CategorySettingsModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categories: List<CategorySettingsModel>)

    @Delete
    fun remove(category: CategorySettingsModel)
}