package com.chekh.paysage.feature.main.domain.mapper

import com.chekh.paysage.data.model.entity.AppSettingsEntity
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.mapper.OneParameterMapper
import javax.inject.Inject

class AppModelMapper @Inject constructor() : OneParameterMapper<AppSettingsEntity, AppModel> {

    override fun map(source: AppSettingsEntity): AppModel {
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