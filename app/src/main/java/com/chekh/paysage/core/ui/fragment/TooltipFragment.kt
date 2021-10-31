package com.chekh.paysage.core.ui.fragment

import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.backpressed.ActionBeforeBackPressedHandler
import com.chekh.paysage.core.handler.backpressed.BackPressedHandler
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.anim.CircularRevealAnimation
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_desktop_options.*
import kotlinx.android.synthetic.main.fragment_tooltip.*
import kotlin.math.min
import kotlin.math.max

open class TooltipFragment(@LayoutRes private val layoutId: Int) : BaseFragment() {

    protected open val args: Args by lazyUnsafe(::getArgs)

    private val tooltipAnimation by lazyUnsafe {
        CircularRevealAnimation(mcvContainer)
    }

    private val backPressedHandler: BackPressedHandler by lazyUnsafe {
        ActionBeforeBackPressedHandler(this, tooltipAnimation::reverse)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_tooltip, container, false).apply {
        inflater.inflate(layoutId, findViewById(R.id.mcvContainer), true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupTooltip()
    }

    override fun onStop() {
        super.onStop()
        tooltipAnimation.cancel()
    }

    private fun setupListeners() {
        tooltipAnimation.doOnEnd { isReverse ->
            if (isReverse) exit()
        }
        flContent.onClick(backPressedHandler::onBackPressed)
    }

    private fun setupTooltip() {
        val contentView = activity?.contentView ?: return
        val windowHeight = contentView.measuredHeight
        val windowWidth = contentView.measuredWidth

        val smallMargin = resources.getDimension(R.dimen.small)
        val mediumRadius = resources.getDimension(R.dimen.medium)

        val rect = when (val args = args) {
            is Args.PointArgs -> args.point.toRect()
            is Args.RectArgs -> args.rect
        }
        val centerY = rect.centerY()

        mcvContainer.measure()
        val tooltipHeight = mcvContainer.measuredHeight
        val tooltipWidth = mcvContainer.measuredWidth

        val shapeBuilder = mcvContainer.shapeAppearanceModel
            .toBuilder()
            .setAllCornerSizes(mediumRadius)

        applyWindowInsets { insets ->
            val rightConstraint = windowWidth - insets.right - tooltipWidth - smallMargin
            val leftConstraint = insets.left + smallMargin
            val topConstraint = insets.top + tooltipHeight + smallMargin
            val bottomConstraint = windowHeight - insets.bottom - tooltipHeight - smallMargin
            val center = when {
                rect.right < rightConstraint && centerY > topConstraint -> {
                    shapeBuilder.setBottomLeftCornerSize(0f)
                    mcvContainer.translationX = rect.right
                    mcvContainer.translationY = centerY - tooltipHeight
                    Point(0, tooltipHeight)
                }
                rect.right < rightConstraint -> {
                    shapeBuilder.setTopLeftCornerSize(0f)
                    mcvContainer.translationX = rect.right
                    mcvContainer.translationY = min(centerY, bottomConstraint)
                    Point(0, 0)
                }
                centerY > topConstraint -> {
                    shapeBuilder.setBottomRightCornerSize(0f)
                    mcvContainer.translationX = max(rect.left - tooltipWidth, leftConstraint)
                    mcvContainer.translationY = centerY - tooltipHeight
                    Point(tooltipWidth, tooltipHeight)
                }
                else -> {
                    shapeBuilder.setTopRightCornerSize(0f)
                    mcvContainer.translationX = max(rect.left - tooltipWidth, leftConstraint)
                    mcvContainer.translationY = min(centerY, bottomConstraint)
                    Point(tooltipWidth, 0)
                }
            }
            mcvContainer.shapeAppearanceModel = shapeBuilder.build()
            tooltipAnimation.start(center)
        }
    }

    override fun onBackPressed() = backPressedHandler.onBackPressed()

    sealed class Args : Parcelable {

        @Parcelize
        open class PointArgs(open val point: PointF) : Args()

        @Parcelize
        open class RectArgs(open val rect: RectF) : Args()
    }

    companion object {

        fun showIn(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
            @LayoutRes layoutId: Int,
            args: Args
        ) = TooltipFragment(layoutId).apply {
            setArgs(args)
            fragmentManager.commit {
                addWithBackStack(containerViewId, this@apply)
            }
        }
    }
}
