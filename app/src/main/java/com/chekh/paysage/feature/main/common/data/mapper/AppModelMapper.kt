package com.chekh.paysage.feature.main.common.data.mapper

import com.chekh.paysage.common.data.model.AppSettingsEntity
import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.feature.main.common.domain.model.AppModel
import javax.inject.Inject

class AppModelMapper @Inject constructor() : OneParameterMapper<AppSettingsEntity, AppModel> {

    override fun map(source: AppSettingsEntity) = AppModel(
        id = source.id,
        packageName = source.packageName,
        className = source.className,
        title = source.title,
        icon = source.icon,
        categoryId = source.categoryId,
        position = source.boardPosition,
        isHidden = source.isHidden,
        iconColor = source.iconColor
    )
}
