package com.chekh.paysage.feature.home

import androidx.lifecycle.ViewModel
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

    fun enableObserveAppsChanging() {
        repository.enableObserveAppsChanging()
    }

    fun disableObserveAppsChanging() {
        repository.disableObserveAppsChanging()
    }
}