package com.chekh.paysage.db.dao

import androidx.room.*
import com.chekh.paysage.feature.home.data.model.PackageSettingsModel

@Dao
interface PackageDao {
    @Query("SELECT categoryId FROM package WHERE name = :packageName")
    fun getCategoryId(packageName: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(packageInfo: PackageSettingsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(packages: List<PackageSettingsModel>)

    @Delete
    fun remove(packageInfo: PackageSettingsModel)
}