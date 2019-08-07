package com.chekh.paysage.viewmodel

import android.os.UserHandle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.db.DatabaseHelper
import com.chekh.paysage.db.PaysageDatabase
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.AppsCategoryInfo
import com.chekh.paysage.util.AppManager
import com.chekh.paysage.util.observe
import com.chekh.paysage.util.zip
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val appDao = PaysageDatabase.instance.appDao()
    private val categoryDao = PaysageDatabase.instance.categoryDao()
    private val appManager = PaysageApp.appManager

    val navigationBarHeightLiveData = MutableLiveData<Int>()
    val statusBarHeightLiveData = MutableLiveData<Int>()

    fun initApps() {
        GlobalScope.launch {
            val activityInfos = appManager.getAllApps()
            DatabaseHelper.updateApps(activityInfos)
        }
    }

    fun getAllApps(owner: LifecycleOwner, callback: (apps: List<AppInfo>) -> Unit) {
        appDao.getLiveAll().observe(owner, callback)
    }

    fun getAllCategories(owner: LifecycleOwner, callback: (categories: List<AppsCategoryInfo>) -> Unit) {
        categoryDao.getLiveAll().observe(owner, callback)
    }

    fun getAppsAndCategories(
        owner: LifecycleOwner,
        callback: (apps: List<AppInfo>, categories: List<AppsCategoryInfo>) -> Unit
    ) {
        zip(appDao.getLiveAll(), categoryDao.getLiveAll()) { apps, categories -> Pair(apps, categories) }
            .observe(owner) { (apps, categories) ->
                if (apps != null && categories != null) {
                    callback(apps, categories)
                }
            }
    }

    fun enableObserveAppsChanging() {
        appManager.addOnAppsChangedCallback(appsChangedCallback)
    }

    fun disableObserveAppsChanging() {
        appManager.addOnAppsChangedCallback(appsChangedCallback)
    }

    private val appsChangedCallback = object : AppManager.AppsChangedCallback() {

        override fun onPackageRemoved(packageName: String, userHandle: UserHandle) {

        }

        override fun onPackageAdded(packageName: String, userHandle: UserHandle) {

        }

        override fun onPackageChanged(packageName: String, userHandle: UserHandle) {

        }
    }
}