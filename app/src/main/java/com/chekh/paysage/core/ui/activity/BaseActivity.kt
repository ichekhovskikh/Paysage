package com.chekh.paysage.core.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.chekh.paysage.core.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.core.ui.fragment.BaseFragment

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    private val backPressedHandler by lazy { ContainerBackPressedHandler(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowInsets()
    }

    private fun setWindowInsets() {
        val content = findViewById<ViewGroup>(android.R.id.content)
        content.setOnApplyWindowInsetsListener { _, insets ->
            onApplyWindowInsets(insets)
            insets.consumeSystemWindowInsets()
        }
    }

    @CallSuper
    open fun onApplyWindowInsets(insets: WindowInsets) {
        val fragments = supportFragmentManager.fragments
        fragments.forEach { fragment ->
            if (fragment is BaseFragment) {
                fragment.onApplyWindowInsets(insets)
            }
        }
    }

    override fun onBackPressed() {
        backPressedHandler.onBackPressed()
    }
}
