package com.chekh.paysage.ui.fragment

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class ViewModelFragment<VM : ViewModel> : BaseFragment() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel(savedInstanceState)
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[viewModelClass]
        onViewModelCreated(savedInstanceState)
    }

    @CallSuper
    protected open fun onViewModelCreated(savedInstanceState: Bundle?) {}
}