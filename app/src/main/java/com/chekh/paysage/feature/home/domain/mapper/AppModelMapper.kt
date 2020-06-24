package com.chekh.paysage.feature.home.domain.mapper

import com.chekh.paysage.feature.home.data.model.AppSettingsModel
import com.chekh.paysage.feature.home.domain.model.AppModel
import com.chekh.paysage.mapper.OneParameterMapper
import javax.inject.Inject

class AppModelMapper @Inject constructor() : OneParameterMapper<AppSettingsModel, AppModel> {

    override fun map(source: AppSettingsModel): AppModel {
        return AppModel(
            id = source.id,
            packageName = source.packageName,
            className = source.className,
            title = source.title,
            icon = source.icon,
            categoryId = source.categoryId,
            position = source.position,
            isHidden = source.isHidden,
            iconColor = source.iconColor
        )
    }
}