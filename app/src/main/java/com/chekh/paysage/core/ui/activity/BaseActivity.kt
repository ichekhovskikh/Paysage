package com.chekh.paysage.core.ui.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.chekh.paysage.core.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.core.tools.lazyUnsafe

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    private val backPressedHandler by lazyUnsafe { ContainerBackPressedHandler(supportFragmentManager) }

    override fun onBackPressed() {
        backPressedHandler.onBackPressed()
    }
}
