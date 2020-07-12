package com.chekh.paysage.feature.home.data.service

import androidx.lifecycle.LiveData
import com.chekh.paysage.db.dao.AppDao
import com.chekh.paysage.db.dao.DockAppDao
import com.chekh.paysage.extension.foreachMap
import com.chekh.paysage.feature.home.domain.mapper.AppModelMapper
import com.chekh.paysage.feature.home.domain.model.AppModel
import javax.inject.Inject

interface DockAppService {
    val dockAppsLiveData: LiveData<List<AppModel>>

    /**
     * first value - app id
     * second value - position
     */
    val dockAppsPositions: LiveData<List<Pair<String, Int>>>
}

class DockAppServiceImpl @Inject constructor(
    appDao: AppDao,
    private val dockAppDao: DockAppDao,
    private val appMapper: AppModelMapper
) : DockAppService {

    override val dockAppsLiveData: LiveData<List<AppModel>> = appDao.getDockAppAllLive()
        .foreachMap { appMapper.map(it) }

    override val dockAppsPositions: LiveData<List<Pair<String, Int>>>
        get() = dockAppDao.getAllLive().foreachMap { it.appId to it.position }
}