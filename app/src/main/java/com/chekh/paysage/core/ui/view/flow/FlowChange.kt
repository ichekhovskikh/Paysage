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
    ) : FlowChange(id, view, 0)

    class Moved(
        id: String,
        view: View,
        val toAlpha: Float,
        val toBounds: Rect
    ) : FlowChange(id, view, 1)

    class Added(
        id: String,
        view: View,
        val toAlpha: Float,
        val bounds: Rect
    ) : FlowChange(id, view, 2)
}
