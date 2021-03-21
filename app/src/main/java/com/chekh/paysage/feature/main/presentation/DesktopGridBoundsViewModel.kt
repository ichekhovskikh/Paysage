package com.chekh.paysage.feature.main.presentation

import android.graphics.Rect
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel

class DesktopGridBoundsViewModel @ViewModelInject constructor() : BaseViewModel<Unit>() {

    val gridBoundsLiveData = MutableLiveData<Rect>()

}
