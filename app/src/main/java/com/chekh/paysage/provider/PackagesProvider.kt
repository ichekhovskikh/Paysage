package com.chekh.paysage.provider

import android.content.Context
import com.chekh.paysage.feature.home.data.model.PackageSettingsModel
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

interface PackagesProvider {
    fun provide(): List<PackageSettingsModel>
}

class PackagesProviderImpl @Inject constructor(
    private val context: Context
) : PackagesProvider {

    override fun provide(): List<PackageSettingsModel> {
        val packages = mutableListOf<PackageSettingsModel>()
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
                packages.add(
                    PackageSettingsModel(
                        packageName,
                        categoryId
                    )
                )
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