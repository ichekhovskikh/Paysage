package com.chekh.paysage.feature.main.data.mapper

import com.chekh.paysage.common.data.model.DesktopPageEntity
import com.chekh.paysage.core.mapper.BidirectionalOneParameterMapper
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import javax.inject.Inject

class DesktopPageModelMapper @Inject constructor() :
    BidirectionalOneParameterMapper<DesktopPageEntity, DesktopPageModel> {

    override fun map(source: DesktopPageEntity) = DesktopPageModel(
        id = source.id,
        position = source.position
    )

    override fun unmap(source: DesktopPageModel) = DesktopPageEntity(
        id = source.id,
        position = source.position
    )
}
