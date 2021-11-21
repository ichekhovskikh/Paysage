package com.chekh.paysage.feature.tooltipdialog.app.presentation

import android.graphics.RectF
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.fragment.TooltipFragment
import kotlinx.android.parcel.Parcelize

class AppTooltipFragment : TooltipFragment(R.layout.fragment_tooltip_app) {

    override val args by lazyArgs<Args>()

    @Parcelize
    data class Args(override val rect: RectF) : TooltipFragment.Args.RectArgs(rect)

    companion object {

        fun showIn(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
            args: Args
        ) {
            fragmentManager.commit {
                addWithBackStack(containerViewId, AppTooltipFragment().applyArgs(args))
            }
        }
    }
}
