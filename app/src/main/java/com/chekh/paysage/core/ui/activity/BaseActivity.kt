package com.chekh.paysage.core.ui.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.chekh.paysage.core.handler.backpressed.ContainerBackPressedHandler

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    private val backPressedHandler by lazy { ContainerBackPressedHandler(supportFragmentManager) }

    override fun onBackPressed() {
        backPressedHandler.onBackPressed()
    }
}
