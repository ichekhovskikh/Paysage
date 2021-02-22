package com.chekh.paysage.feature.main.data.service

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.data.mapper.AppModelMapper
import com.chekh.paysage.feature.main.domain.model.AppModel
import javax.inject.Inject

interface DockAppService {
    val dockApps: LiveData<List<AppModel>>
}

class DockAppServiceImpl @Inject constructor(
    appDao: AppDao,
    private val appMapper: AppModelMapper
) : DockAppService {

    override val dockApps: LiveData<List<AppModel>> =
        appDao.getDockAppAll().foreachMap(appMapper::map)
}
