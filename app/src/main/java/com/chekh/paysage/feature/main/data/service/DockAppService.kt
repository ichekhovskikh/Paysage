package com.chekh.paysage.feature.main.data.service

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.data.mapper.AppModelMapper
import com.chekh.paysage.feature.main.domain.model.AppModel
import javax.inject.Inject

interface DockAppService {
    val dockAppsLiveData: LiveData<List<AppModel>>
}

class DockAppServiceImpl @Inject constructor(
    appDao: AppDao,
    private val appMapper: AppModelMapper
) : DockAppService {

    override val dockAppsLiveData: LiveData<List<AppModel>> =
        appDao.getDockAppAllLive().foreachMap { appMapper.map(it) }
}
