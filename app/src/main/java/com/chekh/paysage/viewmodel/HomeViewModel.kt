package com.chekh.paysage.viewmodel

import android.os.UserHandle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.db.AppRepository
import com.chekh.paysage.model.AppsGroupByCategory
import com.chekh.paysage.util.AppManager
import com.chekh.paysage.util.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val appManager = PaysageApp.appManager

    val navigationBarHeightLiveData = MutableLiveData<Int>()
    val statusBarHeightLiveData = MutableLiveData<Int>()

    fun initApps(onCancelCallback: (() -> Unit)? = null) {
        GlobalScope.launch {
            val activityInfos = appManager.getAllApps()
            AppRepository.reInitApps(activityInfos)
            launch(Dispatchers.Main) { onCancelCallback?.invoke() }
        }
    }

    fun getAppsGroupByCategories(owner: LifecycleOwner, callback: (categories: List<AppsGroupByCategory>) -> Unit) {
        AppRepository.getAppsGroupByCategories().observe(owner, callback)
    }

    fun enableObserveAppsChanging() {
        appManager.addOnAppsChangedCallback(appsChangedCallback)
    }

    fun disableObserveAppsChanging() {
        appManager.addOnAppsChangedCallback(appsChangedCallback)
    }

    private val appsChangedCallback = object : AppManager.AppsChangedCallback() {

        override fun onPackageRemoved(packageName: String, userHandle: UserHandle) {
            GlobalScope.launch {
                AppRepository.removeApps(packageName)
            }
        }

        override fun onPackageAdded(packageName: String, userHandle: UserHandle) {
            onPackageChanged(packageName, userHandle)
        }

        override fun onPackageChanged(packageName: String, userHandle: UserHandle) {
            GlobalScope.launch {
                val activityInfos = appManager.getApps(packageName, userHandle)
                AppRepository.updateApps(activityInfos)

            }
        }
    }
}