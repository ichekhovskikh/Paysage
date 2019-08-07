package com.chekh.paysage.model

import android.content.pm.ApplicationInfo
import com.chekh.paysage.util.readFromAssets
import com.google.gson.Gson

private const val FILE_NAME = "categories.json"

data class AllCategories(
    val version: String,
    val categories: List<Category>
)

data class Category(
    val title: String,
    val packages: List<String>
)

fun getAppsCategoryId(app: ApplicationInfo): String {
    val file = readFromAssets(FILE_NAME)
    val categories = Gson().fromJson<AllCategories>(file, AllCategories::class.java).categories
    val category = categories.firstOrNull { it.packages.contains(app.packageName) }
    val categoryIds = CategoryTitle.values().map { it.id }
    return if (category != null && categoryIds.contains(category.title)) category.title else CategoryTitle.OTHER.id
}
