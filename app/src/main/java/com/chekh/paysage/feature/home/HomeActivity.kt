package com.chekh.paysage.feature.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chekh.paysage.R
import com.chekh.paysage.extension.inTransaction
import com.chekh.paysage.ui.activity.ViewModelActivity
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : ViewModelActivity<HomeViewModel>() {

    override val viewModelClass = HomeViewModel::class.java
    override val layoutId = R.layout.activity_home

    private val statusBarDecorator: StatusBarDecorator by lazy { CommonStatusBarDecorator() }

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarDecorator.setTransparentStatusBar(this)
        addHomeFragmentIfNeed()
    }

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        super.onViewModelCreated(savedInstanceState)
        content.setOnApplyWindowInsetsListener { _, insets ->
            setBarShadow(insets.systemWindowInsetTop, insets.systemWindowInsetBottom)
            viewModel.windowInsets.value = insets
            insets
        }
        viewModel.enableObserveAppsChanging()
        viewModel.initApps()
    }

    private fun addHomeFragmentIfNeed() {
        fragment = supportFragmentManager.fragments.firstOrNull()
        if (fragment == null) {
            val homeFragment = HomeFragment.instance()
            supportFragmentManager.inTransaction {
                add(R.id.container, homeFragment)
            }
            fragment = homeFragment
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
