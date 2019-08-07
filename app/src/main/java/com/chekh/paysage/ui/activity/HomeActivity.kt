package com.chekh.paysage.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chekh.paysage.R
import com.chekh.paysage.ui.fragment.HomeFragment
import com.chekh.paysage.ui.util.inTransaction
import com.chekh.paysage.util.observe
import com.chekh.paysage.ui.util.setTransparentStatusBar
import com.chekh.paysage.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : ViewModelActivity<HomeViewModel>() {

    override val viewModelClass = HomeViewModel::class.java
    override val layoutId = R.layout.activity_home

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
        addFragmentIfNeed()
    }

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            viewModel.statusBarHeightLiveData.observe(this) {
                statusShadow.shadowHeight = it
            }
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            viewModel.navigationBarHeightLiveData.observe(this) {
                navigationShadow.shadowHeight = it
            }
        }
    }

    override fun initViewModel() {
        root.setOnApplyWindowInsetsListener { _, insets ->
            viewModel.statusBarHeightLiveData.value = insets.systemWindowInsetTop
            viewModel.navigationBarHeightLiveData.value = insets.systemWindowInsetBottom
            insets
        }
        viewModel.initApps()
        viewModel.enableObserveAppsChanging()
    }

    private fun addFragmentIfNeed() {
        fragment = supportFragmentManager.fragments.firstOrNull()
        if (fragment == null) {
            fragment = HomeFragment.instance()
            supportFragmentManager.inTransaction {
                add(R.id.content, fragment!!)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disableObserveAppsChanging()
    }
}
