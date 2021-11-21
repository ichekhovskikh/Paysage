package com.chekh.paysage.feature.main.pager.presentation.factory
import com.chekh.paysage.feature.main.common.domain.model.*
import javax.inject.Inject

interface DesktopPageModelFactory {

    fun create(position: Int): DesktopPageModel
}

class DesktopPageModelFactoryImpl @Inject constructor() : DesktopPageModelFactory {

    override fun create(position: Int) = DesktopPageModel(
        position = position
    )
}
