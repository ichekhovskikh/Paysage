package com.chekh.paysage.feature.main.presentation

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.inTransaction
import com.chekh.paysage.core.ui.activity.BaseActivity
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.feature.main.presentation.home.HomeFragment
import com.chekh.paysage.feature.main.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarDecorator.setTransparentStatusBar(this)
        addHomeFragmentIfNeed()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.init(Unit)
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
}
