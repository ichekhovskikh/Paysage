package com.chekh.paysage.db.dao

import androidx.room.*
import com.chekh.paysage.model.PackageInfo

@Dao
interface PackageDao {
    @Query("SELECT categoryId FROM package WHERE name = :packageName")
    fun getCategoryId(packageName: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(packageInfo: PackageInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(packages: List<PackageInfo>)

    @Delete
    fun remove(packageInfo: PackageInfo)
}