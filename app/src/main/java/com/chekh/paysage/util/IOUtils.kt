package com.chekh.paysage.util

import com.chekh.paysage.PaysageApp
import com.chekh.paysage.model.PackageInfo
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

const val CATEGORIES_FILE_NAME = "categories.json"

fun readDefaultPackages(fileName: String): List<PackageInfo> {
    val packages = mutableListOf<PackageInfo>()
    val json = readFromAssets(fileName)
    val jsonObject = JSONObject(json)
    val categoriesJsonObject = jsonObject.getJSONArray("categories")
    val categoriesSize = categoriesJsonObject.length()
    for (categoriesIndex in 0 until categoriesSize) {
        val categoryJsonObject = categoriesJsonObject.getJSONObject(categoriesIndex)
        val categoryId = categoryJsonObject.getString("title")
        val packagesJsonObject = categoryJsonObject.getJSONArray("packages")
        val packagesSize = packagesJsonObject.length()
        for (packagesIndex in 0 until packagesSize) {
            val packageName = packagesJsonObject.getString(packagesIndex)
            packages.add(PackageInfo(packageName, categoryId))
        }
    }
    return packages
}

fun readFromAssets(fileName: String): String {
    val builder = StringBuilder()
    val reader = BufferedReader(InputStreamReader(PaysageApp.launcher.assets.open(fileName)))
    var line = reader.readLine()
    while (line != null) {
        builder.append(line)
        line = reader.readLine()
    }
    reader.close()
    return builder.toString()
}