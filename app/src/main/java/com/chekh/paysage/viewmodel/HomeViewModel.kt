package com.chekh.paysage.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chekh.paysage.db.AppRepository
import com.chekh.paysage.model.AppsGroupByCategory
import com.chekh.paysage.extension.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val navigationBarHeightLiveData = MutableLiveData<Int>()
    val statusBarHeightLiveData = MutableLiveData<Int>()

    fun initApps(onCancelCallback: (() -> Unit)? = null) {
        GlobalScope.launch {
            AppRepository.initApps()
            launch(Dispatchers.Main) { onCancelCallback?.invoke() }
        }
    }

    fun getAppsGroupByCategories(owner: LifecycleOwner, callback: (categories: List<AppsGroupByCategory>) -> Unit) {
        AppRepository.getAppsGroupByCategories().observe(owner, callback)
    }

    fun enableObserveAppsChanging() {
        AppRepository.enableObserveAppsChanging()
    }

    fun disableObserveAppsChanging() {
        AppRepository.disableObserveAppsChanging()
    }
}