package com.chekh.paysage.feature.home.domain.mapper

import android.content.pm.LauncherActivityInfo
import android.content.pm.PackageManager
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.feature.home.data.model.IconColor
import com.chekh.paysage.feature.home.data.model.AppSettingsModel
import com.chekh.paysage.feature.home.domain.model.AppModel
import com.chekh.paysage.mapper.TwoParametersMapper
import javax.inject.Inject

class AppModelMapper @Inject constructor(
    private val packageManager: PackageManager
) : TwoParametersMapper<LauncherActivityInfo, AppSettingsModel, AppModel> {

    override fun map(firstSource: LauncherActivityInfo, secondSource: AppSettingsModel): AppModel {
        val applicationInfo = firstSource.applicationInfo
        val title = applicationInfo.loadLabel(packageManager).toString()
        val icon = applicationInfo.loadIcon(packageManager)
        val iconColor = IconColor.get(icon.toBitmap())

        return AppModel(
            id = secondSource.id,
            packageName = secondSource.packageName,
            className = secondSource.className,
            title = title,
            icon = icon,
            categoryId = secondSource.categoryId,
            position = secondSource.position,
            isHidden = secondSource.isHidden,
            iconColor = iconColor
        )
    }
}