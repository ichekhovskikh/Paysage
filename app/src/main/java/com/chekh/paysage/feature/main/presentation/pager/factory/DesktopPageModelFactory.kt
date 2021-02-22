package com.chekh.paysage.feature.main.presentation.pager.factory
import com.chekh.paysage.feature.main.domain.model.*
import javax.inject.Inject

interface DesktopPageModelFactory {

    fun create(position: Int): DesktopPageModel
}

class DesktopPageModelFactoryImpl @Inject constructor() : DesktopPageModelFactory {

    override fun create(position: Int) = DesktopPageModel(
        position = position
    )
}
