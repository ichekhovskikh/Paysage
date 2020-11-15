package com.chekh.paysage.feature.main.data.service

import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.data.mapper.AppModelMapper
import com.chekh.paysage.feature.main.domain.model.AppModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DockAppService {
    val dockApps: Flow<List<AppModel>>
}

class DockAppServiceImpl @Inject constructor(
    appDao: AppDao,
    private val appMapper: AppModelMapper
) : DockAppService {

    override val dockApps: Flow<List<AppModel>> =
        appDao.getDockAppAllFlow().foreachMap { appMapper.map(it) }
}
