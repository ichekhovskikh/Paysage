package com.chekh.paysage.feature.main.data.service

import androidx.lifecycle.LiveData
import com.chekh.paysage.data.dao.AppDao
import com.chekh.paysage.data.dao.DockAppDao
import com.chekh.paysage.extension.zip
import com.chekh.paysage.feature.main.domain.mapper.AppModelMapper
import com.chekh.paysage.feature.main.domain.model.AppModel
import javax.inject.Inject

interface DockAppService {
    val dockAppsLiveData: LiveData<List<AppModel>>
}

class DockAppServiceImpl @Inject constructor(
    appDao: AppDao,
    dockAppDao: DockAppDao,
    private val appMapper: AppModelMapper
) : DockAppService {

    override val dockAppsLiveData: LiveData<List<AppModel>> =
        zip(
            appDao.getDockAppAllLive(),
            dockAppDao.getAllLive()
        ) { apps, settings ->
            apps
                .sortedBy { settings.find { (id, _) -> id == it.id }?.position }
                .map { appMapper.map(it) }
        }
}