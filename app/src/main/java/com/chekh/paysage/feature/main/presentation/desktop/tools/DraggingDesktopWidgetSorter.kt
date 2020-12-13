package com.chekh.paysage.feature.main.presentation.desktop.tools

import android.graphics.Rect
import com.chekh.paysage.core.extension.copyOffset
import com.chekh.paysage.core.extension.isIntersect
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.round

class DraggingDesktopWidgetSorter @Inject constructor() {

    private var spanCount: Int? = null
    private var maxWidgetWidth: Int? = null
    private var draggingWidget: DesktopWidgetModel? = null
    private var onSortOrderChanged: (() -> Unit)? = null

    private val columnWidth: Int?
        get() {
            val spanCount = spanCount ?: return null
            val maxWidgetWidth = maxWidgetWidth ?: return null
            return maxWidgetWidth / spanCount
        }

    fun setOnSortOrderChangedListener(listener: (() -> Unit)? = null) {
        onSortOrderChanged = listener
    }

    fun setSpanCount(spanCount: Int?) {
        val oldSpanCount = this.spanCount
        this.spanCount = spanCount
        if (oldSpanCount != spanCount) {
            onSortOrderChanged?.invoke()
        }
    }

    fun setMaxWidgetWidth(maxWidgetWidth: Int?) {
        val oldMaxWidgetWidth = this.maxWidgetWidth
        this.maxWidgetWidth = maxWidgetWidth
        if (oldMaxWidgetWidth != maxWidgetWidth) {
            onSortOrderChanged?.invoke()
        }
    }

    fun setDraggingWidget(draggingWidget: DesktopWidgetModel?) {
        val oldDraggingIndexedBounds = this.draggingWidget?.bounds?.toIndexedBounds()
        val newDraggingIndexedBounds = draggingWidget?.bounds?.toIndexedBounds()
        this.draggingWidget = draggingWidget
        if (oldDraggingIndexedBounds != newDraggingIndexedBounds) {
            onSortOrderChanged?.invoke()
        }
    }

    fun getSorted(unsortedWidgets: List<DesktopWidgetModel>?): List<DesktopWidgetModel>? {
        val draggingWidget = draggingWidget ?: return unsortedWidgets
        val desktopWidgets = unsortedWidgets?.filter { it.id != draggingWidget.id } ?: return null
        val indexedDraggingWidget = draggingWidget.toIndexedWidget()
        val indexedDesktopWidgets = desktopWidgets.map { it.toIndexedWidget() }

        if (!isOccupiedSpace(indexedDraggingWidget, indexedDesktopWidgets)) {
            return desktopWidgets + draggingWidget
        }

        val allIndexedWidgets = indexedDesktopWidgets + indexedDraggingWidget
        val sortedHorizontalWidgets = sortHorizontal(indexedDraggingWidget, allIndexedWidgets)
        if (sortedHorizontalWidgets != null) {
            return sortedHorizontalWidgets
        }

        val sortedVerticalWidgets = sortVertical(indexedDraggingWidget, allIndexedWidgets)
        return sortedVerticalWidgets ?: desktopWidgets
    }

    private fun isOccupiedSpace(
        draggingWidget: IndexedDesktopWidget,
        widgets: List<IndexedDesktopWidget>
    ): Boolean = widgets.any { it.indexedBounds.isIntersect(draggingWidget.indexedBounds) }

    private fun sortHorizontal(
        draggingWidget: IndexedDesktopWidget,
        widgets: List<IndexedDesktopWidget>,
    ): List<DesktopWidgetModel>? {
        val moves = moveHorizontal(draggingWidget, widgets)
        return moves?.let { applyMoves(widgets, moves) }
    }

    private fun moveHorizontal(
        moveWidget: IndexedDesktopWidget,
        widgets: List<IndexedDesktopWidget>,
        preMoves: List<Move> = emptyList(),
        steps: Int = 0
    ): List<Move>? {
        if (moveWidget.desktopWidget.isDragging && preMoves.isNotEmpty()) return null
        val spanCount = spanCount ?: 1
        val preStepMove = preMoves.find { it.widgetId == moveWidget.desktopWidget.id }
        val stepMove = when (preStepMove) {
            null -> Move(moveWidget.desktopWidget.id, horizontal = steps)
            else -> preStepMove.copy(horizontal = preStepMove.horizontal + steps)
        }

        var currentMoves = preMoves.filter { it != preStepMove } + stepMove
        val movedIndexedBounds = moveWidget.getMovedIndexedBounds(stepMove)
        if (movedIndexedBounds.left < 0 || movedIndexedBounds.right > spanCount) return null

        for (widget in widgets) {
            if (widget.desktopWidget.id == moveWidget.desktopWidget.id) continue
            val widgetMove = preMoves.find { it.widgetId == widget.desktopWidget.id }
            val widgetIndexedBounds = widget.getMovedIndexedBounds(widgetMove)
            if (!widgetIndexedBounds.isIntersect(movedIndexedBounds)) continue

            val rightSteps = movedIndexedBounds.right - widgetIndexedBounds.left
            val leftSteps = movedIndexedBounds.left - widgetIndexedBounds.right
            val minSteps = if (abs(rightSteps) <= abs(leftSteps)) rightSteps else leftSteps
            val maxSteps = if (abs(rightSteps) > abs(leftSteps)) rightSteps else leftSteps

            var newMoves = moveHorizontal(widget, widgets, currentMoves, minSteps)
            if (newMoves == null) {
                newMoves = moveHorizontal(widget, widgets, currentMoves, maxSteps)
            }
            currentMoves = newMoves ?: return null

        }
        return currentMoves
    }

    private fun sortVertical(
        draggingWidget: IndexedDesktopWidget,
        widgets: List<IndexedDesktopWidget>,
    ): List<DesktopWidgetModel>? {
        val minIntersectedWidgetBottom = widgets
            .filter {
                it.indexedBounds.top < draggingWidget.indexedBounds.top &&
                    it.indexedBounds.isIntersect(draggingWidget.indexedBounds)
            }
            .minOfOrNull { it.indexedBounds.bottom }

        val steps = when (minIntersectedWidgetBottom) {
            null -> 0
            else -> minIntersectedWidgetBottom - draggingWidget.indexedBounds.top
        }
        val moves = moveVertical(draggingWidget, widgets, steps = steps)
        return moves?.let { applyMoves(widgets, moves) }
    }

    private fun moveVertical(
        moveWidget: IndexedDesktopWidget,
        widgets: List<IndexedDesktopWidget>,
        preMoves: List<Move> = emptyList(),
        steps: Int = 0
    ): List<Move>? {
        if (moveWidget.desktopWidget.isDragging && preMoves.isNotEmpty()) return null
        val preStepMove = preMoves.find { it.widgetId == moveWidget.desktopWidget.id }
        val stepMove = when (preStepMove) {
            null -> Move(moveWidget.desktopWidget.id, vertical = steps)
            else -> preStepMove.copy(vertical = preStepMove.vertical + steps)
        }

        var currentMoves = preMoves.filter { it != preStepMove } + stepMove
        val movedIndexedBounds = moveWidget.getMovedIndexedBounds(stepMove)
        if (movedIndexedBounds.top < 0) return null

        for (widget in widgets) {
            if (widget.desktopWidget.id == moveWidget.desktopWidget.id) continue
            val widgetMove = preMoves.find { it.widgetId == widget.desktopWidget.id }
            val widgetIndexedBounds = widget.getMovedIndexedBounds(widgetMove)
            if (!widgetIndexedBounds.isIntersect(movedIndexedBounds)) continue

            val bottomSteps = movedIndexedBounds.bottom - widgetIndexedBounds.top
            currentMoves = moveVertical(widget, widgets, currentMoves, bottomSteps) ?: return null

        }
        return currentMoves
    }

    private fun applyMoves(
        widgets: List<IndexedDesktopWidget>,
        moves: List<Move>
    ): List<DesktopWidgetModel> = mutableListOf<DesktopWidgetModel>().apply {
        for ((widget, _) in widgets) {
            val move = moves.find { it.widgetId == widget.id }
            val sortedWidget = when (move?.hasOffset) {
                true -> widget.copy(
                    bounds = widget.bounds.copyOffset(
                        move.horizontal * (columnWidth ?: 1),
                        move.vertical * (columnWidth ?: 1)
                    )
                )
                else -> widget
            }
            add(sortedWidget)
        }
    }

    private data class IndexedDesktopWidget(
        val desktopWidget: DesktopWidgetModel,
        val indexedBounds: Rect
    )

    private data class Move(
        val widgetId: String,
        val vertical: Int = 0,
        val horizontal: Int = 0
    )

    private fun DesktopWidgetModel.toIndexedWidget() =
        IndexedDesktopWidget(this, this.bounds.toIndexedBounds())

    private fun Rect.toIndexedBounds(): Rect {
        val spanCount = spanCount ?: return this
        val columnWidth = columnWidth ?: return this
        val left = round(left.toFloat() / columnWidth).toInt()
        val top = round(top.toFloat() / columnWidth).toInt()
        val right = left + round(width().toFloat() / columnWidth).toInt()
        val bottom = top + round(height().toFloat() / columnWidth).toInt()

        val dy = if (top < 0) -top else 0
        val dx = when {
            right > spanCount -> spanCount - right
            left < 0 -> -left
            else -> 0
        }
        return Rect(
            left + dx,
            top + dy,
            right + dx,
            bottom + dy
        )
    }

    private fun IndexedDesktopWidget.getMovedIndexedBounds(move: Move?) = when (move) {
        null -> indexedBounds
        else -> indexedBounds.copyOffset(move.horizontal, move.vertical)
    }

    private val Move.hasOffset get() = vertical != 0 || horizontal != 0
}
