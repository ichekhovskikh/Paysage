package com.chekh.paysage.data.dao

import androidx.room.*
import com.chekh.paysage.data.model.entity.PackageSettingsEntity

@Dao
interface PackageDao {
    @Query("SELECT categoryId FROM package WHERE name = :packageName")
    fun getCategoryId(packageName: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(packageInfo: PackageSettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(packages: List<PackageSettingsEntity>)

    @Delete
    fun remove(packageInfo: PackageSettingsEntity)
}