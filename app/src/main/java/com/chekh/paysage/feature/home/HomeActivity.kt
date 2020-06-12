package com.chekh.paysage.feature.home

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.fragment.app.Fragment
import com.chekh.paysage.R
import com.chekh.paysage.extension.inTransaction
import com.chekh.paysage.ui.activity.ViewModelActivity
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : ViewModelActivity<HomeViewModel>() {

    override val viewModelClass = HomeViewModel::class
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
        viewModel.enableObserveAppsChanging()
        viewModel.initApps()
    }

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        setBarShadow(insets.systemWindowInsetTop, insets.systemWindowInsetBottom)
    }

    private fun addHomeFragmentIfNeed() {
        fragment = supportFragmentManager.fragments.firstOrNull()
        if (fragment == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.inTransaction {
                add(R.id.flContainer, homeFragment)
            }
            fragment = homeFragment
        }
    }

    private fun setBarShadow(statusShadowHeight: Int, navigationShadowHeight: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            swStatusBarShadow.shadowHeight = statusShadowHeight
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            swNavigationBarShadow.shadowHeight = navigationShadowHeight
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disableObserveAppsChanging()
    }
}
