package com.chekh.paysage.feature.home

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.screen.apps.model.AppsGroupByCategory

interface AppRepository {

    fun initApps()

    fun getAppsGroupByCategories(): LiveData<List<AppsGroupByCategory>>

    fun enableObserveAppsChanging()

    fun disableObserveAppsChanging()

}