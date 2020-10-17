package com.chekh.paysage.feature.main

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.fragment.app.Fragment
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.inTransaction
import com.chekh.paysage.core.ui.activity.ViewModelActivity
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.feature.main.screen.home.HomeFragment
import com.chekh.paysage.feature.main.screen.home.HomeViewModel
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : ViewModelActivity<HomeViewModel>(
    R.layout.activity_home,
    HomeViewModel::class
) {

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarDecorator.setTransparentStatusBar(this)
        addHomeFragmentIfNeed()
    }

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        super.onViewModelCreated(savedInstanceState)
        viewModel.startObserveUpdates()
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
        viewModel.stopObserveUpdates()
    }
}
