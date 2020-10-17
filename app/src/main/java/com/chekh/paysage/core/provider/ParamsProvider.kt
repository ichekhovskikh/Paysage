package com.chekh.paysage.core.provider

import android.os.Bundle
import androidx.fragment.app.Fragment
import javax.inject.Inject

interface ParamsProvider {
    fun provide(fragment: Fragment): Bundle
}

class ParamsProviderIml @Inject constructor() : ParamsProvider {

    override fun provide(fragment: Fragment): Bundle {
        val arguments = fragment.arguments ?: Bundle.EMPTY
        val activityBundle = fragment.activity?.intent?.extras ?: Bundle.EMPTY
        return Bundle(activityBundle).apply { putAll(arguments) }
    }
}
