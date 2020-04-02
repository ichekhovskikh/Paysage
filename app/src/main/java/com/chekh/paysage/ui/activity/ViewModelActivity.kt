package com.chekh.paysage.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chekh.paysage.handler.backpressed.ContainerBackPressedHandler

abstract class ViewModelActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>

    @get:LayoutRes
    protected abstract val layoutId: Int

    private val backPressedHandler by lazy { ContainerBackPressedHandler(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        createViewModel(savedInstanceState)
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[viewModelClass]
        onViewModelCreated(savedInstanceState)
    }

    @CallSuper
    protected open fun onViewModelCreated(savedInstanceState: Bundle?) {}

    override fun onBackPressed() {
        backPressedHandler.onBackPressed()
    }
}
