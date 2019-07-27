package com.chekh.paysage.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.chekh.paysage.ui.fragment.core.BaseFragment

abstract class ViewModelActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        createViewModel(savedInstanceState)
        initViewModel()
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[viewModelClass]
        onViewModelCreated(savedInstanceState)
    }

    protected open fun onViewModelCreated(savedInstanceState: Bundle?) {}

    protected open fun initViewModel() {}

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        var handled = false
        for (fragment in fragments) {
            if (fragment is BaseFragment) {
                handled = fragment.onBackPressed()
                if (handled) break
            }
        }
        if (!handled) {
            super.onBackPressed()
        }
    }
}
