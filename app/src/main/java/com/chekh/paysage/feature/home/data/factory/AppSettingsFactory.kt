package com.chekh.paysage.feature.home.data.factory

import android.content.ComponentName
import com.chekh.paysage.feature.home.data.model.AppSettingsModel
import javax.inject.Inject

interface AppSettingsFactory {

    fun create(
        componentName: ComponentName,
        categoryId: String,
        position: Int,
        isHidden: Boolean = false
    ): AppSettingsModel
}

class AppSettingsFactoryImpl @Inject constructor() : AppSettingsFactory {

    override fun create(
        componentName: ComponentName,
        categoryId: String,
        position: Int,
        isHidden: Boolean
    ): AppSettingsModel {
        val packageName = componentName.packageName
        val className = componentName.className
        val id = packageName + className
        return AppSettingsModel(
            id = id,
            packageName = packageName,
            className = className,
            categoryId = categoryId,
            position = position,
            isHidden = isHidden
        )
    }
}