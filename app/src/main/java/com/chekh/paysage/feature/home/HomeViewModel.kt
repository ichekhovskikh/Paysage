package com.chekh.paysage.feature.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.chekh.paysage.feature.home.apps.model.AppsGroupByCategory
import com.chekh.paysage.extension.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository: AppRepository by lazy { AppRepositoryImpl() }

    fun initApps(onCancelCallback: (() -> Unit)? = null) {
        GlobalScope.launch {
            repository.initApps()
            launch(Dispatchers.Main) { onCancelCallback?.invoke() }
        }
    }

    fun getAppsGroupByCategories(owner: LifecycleOwner, callback: (categories: List<AppsGroupByCategory>) -> Unit) {
        repository.getAppsGroupByCategories().observe(owner, callback)
    }

    fun enableObserveAppsChanging() {
        repository.enableObserveAppsChanging()
    }

    fun disableObserveAppsChanging() {
        repository.disableObserveAppsChanging()
    }
}