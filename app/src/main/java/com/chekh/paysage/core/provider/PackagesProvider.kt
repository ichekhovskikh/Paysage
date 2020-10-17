package com.chekh.paysage.core.provider

import android.content.Context
import com.chekh.paysage.common.data.model.PackageSettingsEntity
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import org.json.JSONObject

interface PackagesProvider {
    fun provide(): List<PackageSettingsEntity>
}

class PackagesProviderImpl @Inject constructor(
    private val context: Context
) : PackagesProvider {

    override fun provide(): List<PackageSettingsEntity> {
        val packages = mutableListOf<PackageSettingsEntity>()
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
                    PackageSettingsEntity(
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

    private companion object {
        const val CATEGORIES_FILE_NAME = "categories.json"
        const val CATEGORIES_NAME = "categories"
        const val TITLE_NAME = "title"
        const val PACKAGES_NAME = "packages"
    }
}
