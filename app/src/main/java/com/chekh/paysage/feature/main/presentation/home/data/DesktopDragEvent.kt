package com.chekh.paysage.feature.main.presentation.home.data

import android.graphics.RectF
import com.chekh.paysage.core.ui.view.drag.ClipData

sealed class DesktopDragEvent(open val pageId: Long) {

    data class Start(
        override val pageId: Long,
        val location: RectF,
        val data: ClipData?
    ) : DesktopDragEvent(pageId)

    data class Move(
        override val pageId: Long,
        val location: RectF,
        val data: ClipData?
    ) : DesktopDragEvent(pageId)

    data class End(
        override val pageId: Long,
        val location: RectF,
        val data: ClipData?
    ) : DesktopDragEvent(pageId)

    fun copyEvent(pageId: Long = this.pageId): DesktopDragEvent = when(this) {
        is Start -> copy(pageId)
        is Move -> copy(pageId)
        is End -> copy(pageId)
    }
}
