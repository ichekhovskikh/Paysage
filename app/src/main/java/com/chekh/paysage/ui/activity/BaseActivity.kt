package com.chekh.paysage.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.chekh.paysage.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.ui.fragment.BaseFragment

abstract class BaseActivity : AppCompatActivity() {

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
            insets
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
