package com.chekh.paysage.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chekh.paysage.model.AppsCategoryInfo

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: Long): AppsCategoryInfo

    @Query("SELECT * FROM category")
    fun getAll(): LiveData<List<AppsCategoryInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(category: AppsCategoryInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(category: List<AppsCategoryInfo>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: AppsCategoryInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: List<AppsCategoryInfo>)

    @Delete
    fun remove(category: AppsCategoryInfo)
}