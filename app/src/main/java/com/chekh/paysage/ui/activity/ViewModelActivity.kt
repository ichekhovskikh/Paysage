package com.chekh.paysage.ui.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import com.chekh.paysage.di.tools.ViewModelFactory
import com.chekh.paysage.extension.get
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class ViewModelActivity<VM : ViewModel>(
    @LayoutRes layoutId: Int,
    private val viewModelClass: KClass<VM>
) : BaseActivity(layoutId) {

    protected lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel(savedInstanceState)
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        viewModel = viewModelFactory.get(this, viewModelClass)
        onViewModelCreated(savedInstanceState)
    }

    @CallSuper
    protected open fun onViewModelCreated(savedInstanceState: Bundle?) {
    }
}
