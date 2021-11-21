package com.chekh.paysage.feature.main.common.data.datasource

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.common.data.mapper.AppModelMapper
import com.chekh.paysage.feature.main.common.domain.model.AppModel
import javax.inject.Inject

interface DockAppDataSource {
    val dockApps: LiveData<List<AppModel>>
}

class DockAppDataSourceImpl @Inject constructor(
    appDao: AppDao,
    private val appMapper: AppModelMapper
) : DockAppDataSource {

    override val dockApps: LiveData<List<AppModel>> =
        appDao.getDockAppAll().foreachMap(appMapper::map)
}
