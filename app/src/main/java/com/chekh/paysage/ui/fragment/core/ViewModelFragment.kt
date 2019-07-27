package com.chekh.paysage.ui.fragment.core

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class ViewModelFragment<VM : ViewModel> : BaseFragment() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel(savedInstanceState)
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!)[viewModelClass]
        onViewModelCreated(savedInstanceState)
    }

    protected open fun onViewModelCreated(savedInstanceState: Bundle?) {}
}