package com.chekh.paysage.feature.main.common.data.datasource

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.DesktopPageDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.common.data.mapper.DesktopPageModelMapper
import com.chekh.paysage.feature.main.common.domain.model.DesktopPageModel
import javax.inject.Inject

interface DesktopPageDataSource {

    val desktopPages: LiveData<List<DesktopPageModel>>

    suspend fun addDesktopPage(page: DesktopPageModel)
    suspend fun removeDesktopPageByPosition(position: Int)
}

class DesktopPageDataSourceImpl @Inject constructor(
    private val desktopPageDao: DesktopPageDao,
    private val desktopPageMapper: DesktopPageModelMapper
) : DesktopPageDataSource {

    override val desktopPages: LiveData<List<DesktopPageModel>> = desktopPageDao.getAll()
        .foreachMap(desktopPageMapper::map)

    override suspend fun addDesktopPage(page: DesktopPageModel) {
        desktopPageDao.add(desktopPageMapper.unmap(page))
    }

    override suspend fun removeDesktopPageByPosition(position: Int) {
        desktopPageDao.removeByPositionWithShift(position)
    }
}
