package com.chekh.paysage.feature.main.data.service

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.DesktopPageDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.data.mapper.DesktopPageModelMapper
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import javax.inject.Inject

interface DesktopPageService {

    val desktopPages: LiveData<List<DesktopPageModel>>

    suspend fun addDesktopPage(page: DesktopPageModel)
    suspend fun removeDesktopPageByPosition(position: Int)
}

class DesktopPageServiceImpl @Inject constructor(
    private val desktopPageDao: DesktopPageDao,
    private val desktopPageMapper: DesktopPageModelMapper
) : DesktopPageService {

    override val desktopPages: LiveData<List<DesktopPageModel>> = desktopPageDao.getAll()
        .foreachMap(desktopPageMapper::map)

    override suspend fun addDesktopPage(page: DesktopPageModel) {
        desktopPageDao.add(desktopPageMapper.unmap(page))
    }

    override suspend fun removeDesktopPageByPosition(position: Int) {
        desktopPageDao.removeByPositionWithShift(position)
    }
}
