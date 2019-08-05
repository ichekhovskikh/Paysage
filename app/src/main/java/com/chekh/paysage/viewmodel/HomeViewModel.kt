package com.chekh.paysage.viewmodel

import android.os.UserHandle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.db.DatabaseHelper
import com.chekh.paysage.db.PaysageDatabase
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.AppsCategoryInfo
import com.chekh.paysage.util.AppManager
import com.chekh.paysage.util.uiObserve
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val appDao = PaysageDatabase.instance.appDao()
    private val categoryDao = PaysageDatabase.instance.categoryDao()
    private val appManager = PaysageApp.appManager

    val navigationBarHeightLiveData = MutableLiveData<Int>()
    val statusBarHeightLiveData = MutableLiveData<Int>()

    fun getAllApps(owner: LifecycleOwner, callback: (apps: List<AppInfo>) -> Unit) {
        GlobalScope.launch {
            appDao.getLiveAll().uiObserve(owner, callback)
            val activityInfos = appManager.getAllApps()
            DatabaseHelper.updateApps(activityInfos)
        }
    }

    fun getAllCategories(owner: LifecycleOwner, callback: (apps: List<AppsCategoryInfo>) -> Unit) {
        GlobalScope.launch {
            categoryDao.getLiveAll().uiObserve(owner, callback)
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