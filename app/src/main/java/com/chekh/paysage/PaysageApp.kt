package com.chekh.paysage

import android.app.Application
import io.alterac.blurkit.BlurKit

class PaysageApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        BlurKit.init(this)
    }

    companion object {
        lateinit var instance: PaysageApp private set
    }
}