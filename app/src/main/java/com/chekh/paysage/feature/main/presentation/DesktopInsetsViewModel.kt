package com.chekh.paysage.feature.main.presentation

import android.view.WindowInsets
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel

class DesktopInsetsViewModel @ViewModelInject constructor() : BaseViewModel<Unit>() {

    val windowInsets = MutableLiveData<WindowInsets>()

}
