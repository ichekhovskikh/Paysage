package com.chekh.paysage.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chekh.paysage.R
import com.chekh.paysage.extension.inTransaction
import com.chekh.paysage.extension.setTransparentStatusBar
import com.chekh.paysage.ui.fragment.HomeFragment
import com.chekh.paysage.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : ViewModelActivity<HomeViewModel>() {

    override val viewModelClass = HomeViewModel::class.java
    override val layoutId = R.layout.activity_home

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
        addHomeFragmentIfNeed()
    }

    override fun initViewModel() {
        rootLayout.setOnApplyWindowInsetsListener { _, insets ->
            setBarShadow(insets.systemWindowInsetTop, insets.systemWindowInsetBottom)
            viewModel.statusBarHeightLiveData.value = insets.systemWindowInsetTop
            viewModel.navigationBarHeightLiveData.value = insets.systemWindowInsetBottom
            insets
        }
        viewModel.enableObserveAppsChanging()
        viewModel.initApps()
    }

    private fun addHomeFragmentIfNeed() {
        fragment = supportFragmentManager.fragments.firstOrNull()
        if (fragment == null) {
            fragment = HomeFragment.instance()
            supportFragmentManager.inTransaction {
                add(R.id.content, fragment!!)
            }
        }
    }

    private fun setBarShadow(statusShadowHeight: Int, navigationShadowHeight: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            statusShadow.shadowHeight = statusShadowHeight
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            navigationShadow.shadowHeight = navigationShadowHeight
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disableObserveAppsChanging()
    }
}
