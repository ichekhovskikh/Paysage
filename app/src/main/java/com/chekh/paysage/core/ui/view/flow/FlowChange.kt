package com.chekh.paysage.core.ui.view.flow

import android.graphics.Rect
import android.view.View

internal sealed class FlowChange(
    val id: String,
    val view: View,
    val priority: Int
) {

    class Removed(
        id: String,
        view: View
    ) : FlowChange(id, view, PRIORITY_LOW)

    class Moved(
        id: String,
        view: View,
        val toAlpha: Float,
        val toBounds: Rect
    ) : FlowChange(id, view, PRIORITY_MEDIUM)

    class Added(
        id: String,
        view: View,
        val toAlpha: Float,
        val bounds: Rect
    ) : FlowChange(id, view, PRIORITY_HIGH)

    companion object {
        const val PRIORITY_LOW = 0
        const val PRIORITY_MEDIUM = 1
        const val PRIORITY_HIGH = 2
    }
}
