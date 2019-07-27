package com.chekh.paysage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val navigationBarHeightLiveData = MutableLiveData<Int>()
    val statusBarHeightLiveData = MutableLiveData<Int>()
}