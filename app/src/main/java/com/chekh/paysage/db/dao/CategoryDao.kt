package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.feature.home.apps.model.AppsGroupByCategory

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: String): CategoryInfo

    @Query("SELECT * FROM category")
    fun getAll(): List<CategoryInfo>

    @Query("SELECT * FROM category")
    fun getLiveAll(): LiveData<List<CategoryInfo>>

    @Transaction
    @Query("SELECT * FROM category WHERE category.id IN (SELECT categoryId FROM app)")
    fun getGroupByCategories(): LiveData<List<AppsGroupByCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(category: CategoryInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(categories: List<CategoryInfo>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: CategoryInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categories: List<CategoryInfo>)

    @Delete
    fun remove(category: CategoryInfo)
}