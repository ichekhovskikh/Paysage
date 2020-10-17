package com.chekh.paysage.core.ui.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chekh.paysage.core.extension.recharge

abstract class BaseViewModel<TriggerType> : ViewModel() {

    val trigger = MutableLiveData<TriggerType>()

    @CallSuper
    open fun init(trigger: TriggerType) {
        this.trigger.value = trigger
    }

    open fun retry() {
        trigger.recharge()
    }
}
