package com.chekh.paysage.ui.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class ViewModelActivity<VM : ViewModel> : BaseActivity() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel(savedInstanceState)
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[viewModelClass]
        onViewModelCreated(savedInstanceState)
    }

    @CallSuper
    protected open fun onViewModelCreated(savedInstanceState: Bundle?) {
    }
}
