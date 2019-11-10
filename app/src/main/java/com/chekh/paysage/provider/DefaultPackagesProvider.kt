package com.chekh.paysage.provider

import com.chekh.paysage.PaysageApp
import com.chekh.paysage.model.PackageInfo
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

object DefaultPackagesProvider {

    private const val CATEGORIES_FILE_NAME = "categories.json"
    private const val CATEGORIES_NAME = "categories"
    private const val TITLE_NAME = "title"
    private const val PACKAGES_NAME = "packages"

    fun getDefaultPackages(): List<PackageInfo> {
        val packages = mutableListOf<PackageInfo>()
        val json =
            readDefaultPackagesFromAssets()
        val jsonObject = JSONObject(json)
        val categoriesJsonObject = jsonObject.getJSONArray(CATEGORIES_NAME)
        val categoriesSize = categoriesJsonObject.length()
        for (categoriesIndex in 0 until categoriesSize) {
            val categoryJsonObject = categoriesJsonObject.getJSONObject(categoriesIndex)
            val categoryId = categoryJsonObject.getString(TITLE_NAME)
            val packagesJsonObject = categoryJsonObject.getJSONArray(PACKAGES_NAME)
            val packagesSize = packagesJsonObject.length()
            for (packagesIndex in 0 until packagesSize) {
                val packageName = packagesJsonObject.getString(packagesIndex)
                packages.add(PackageInfo(packageName, categoryId))
            }
        }
        return packages
    }

    private fun readDefaultPackagesFromAssets(): String {
        val builder = StringBuilder()
        val asset = PaysageApp.launcher.assets.open(CATEGORIES_FILE_NAME)
        val reader = BufferedReader(InputStreamReader(asset))
        var line = reader.readLine()
        while (line != null) {
            builder.append(line)
            line = reader.readLine()
        }
        reader.close()
        return builder.toString()
    }
}