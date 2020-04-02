package com.chekh.paysage.provider

import android.content.Context
import com.chekh.paysage.model.PackageInfo
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class PackagesProvider(private val context: Context) : Provider<List<PackageInfo>> {

    override fun provide(): List<PackageInfo> {
        val packages = mutableListOf<PackageInfo>()
        val json = readDefaultPackagesFromAssets()
        val categoriesJsonObject = JSONObject(json).getJSONArray(CATEGORIES_NAME)
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
        val asset = context.assets.open(CATEGORIES_FILE_NAME)
        val reader = BufferedReader(InputStreamReader(asset))
        var line = reader.readLine()
        while (line != null) {
            builder.append(line)
            line = reader.readLine()
        }
        reader.close()
        return builder.toString()
    }

    companion object {
        private const val CATEGORIES_FILE_NAME = "categories.json"
        private const val CATEGORIES_NAME = "categories"
        private const val TITLE_NAME = "title"
        private const val PACKAGES_NAME = "packages"
    }
}