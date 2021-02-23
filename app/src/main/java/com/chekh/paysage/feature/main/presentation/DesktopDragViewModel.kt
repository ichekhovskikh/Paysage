package com.chekh.paysage.feature.main.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.presentation.home.data.DesktopDragEvent

class DesktopDragViewModel @ViewModelInject constructor() : BaseViewModel<Unit>() {

    val dragEventLiveData = MutableLiveData<DesktopDragEvent>()

}
