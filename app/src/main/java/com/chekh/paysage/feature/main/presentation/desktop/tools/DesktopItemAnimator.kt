package com.chekh.paysage.feature.main.presentation.desktop.tools

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class DesktopItemAnimator : DefaultItemAnimator() {

    override fun animateMove(
        holder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        val view = holder.itemView
        val deltaX = toX - fromX
        val deltaY = toY - fromY
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder)
            return false
        }
        view.animate().cancel()
        if (deltaX != 0) {
            view.translationX -= deltaX.toFloat()
        }
        if (deltaY != 0) {
            view.translationY -= deltaY.toFloat()
        }
        view.animate()
            .translationX(0f)
            .translationY(0f)
            .setDuration(moveDuration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animator: Animator) {
                        dispatchMoveStarting(holder)
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        view.animate().setListener(null)
                        dispatchMoveFinished(holder)
                        if (!isRunning) {
                            dispatchAnimationsFinished()
                        }
                    }
                }
            )
            .start()
        return true
    }
}
