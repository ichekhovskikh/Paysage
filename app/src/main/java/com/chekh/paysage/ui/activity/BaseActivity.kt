package com.chekh.paysage.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.chekh.paysage.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.ui.fragment.BaseFragment
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    private val backPressedHandler by lazy { ContainerBackPressedHandler(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
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
