package com.chekh.paysage.ui.fragment

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.chekh.paysage.di.tools.ViewModelFactory
import com.chekh.paysage.extension.get
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class ViewModelFragment<VM : ViewModel> : BaseFragment() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: KClass<VM>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel(savedInstanceState)
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        val activity = activity ?: return
        viewModel = viewModelFactory.get(activity, viewModelClass)
        onViewModelCreated(savedInstanceState)
    }

    @CallSuper
    protected open fun onViewModelCreated(savedInstanceState: Bundle?) {}
}