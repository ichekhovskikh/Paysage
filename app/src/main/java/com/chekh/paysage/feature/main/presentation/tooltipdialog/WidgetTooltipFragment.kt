package com.chekh.paysage.feature.main.presentation.tooltipdialog

import android.graphics.PointF
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.addWithBackStack
import com.chekh.paysage.core.extension.getArgs
import com.chekh.paysage.core.extension.setArgs
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.fragment.TooltipFragment
import kotlinx.android.parcel.Parcelize

open class WidgetTooltipFragment : TooltipFragment(R.layout.fragment_tooltip_widget) {

    override val args: Args by lazyUnsafe(::getArgs)

    @Parcelize
    data class Args(override val point: PointF) : TooltipFragment.Args.PointArgs(point)

    companion object {

        fun showIn(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
            args: Args
        ) = WidgetTooltipFragment().apply {
            setArgs(args)
            fragmentManager.commit {
                addWithBackStack(containerViewId, this@apply)
            }
        }
    }
}
