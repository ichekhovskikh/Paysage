package com.chekh.paysage.feature.main.presentation.desktop.tools

import android.graphics.Rect
import com.chekh.paysage.core.extension.copy
import com.chekh.paysage.core.extension.copyOffset
import com.chekh.paysage.core.extension.isIntersect
import com.chekh.paysage.core.ui.tools.Size
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign

internal fun List<DesktopWidgetModel>.sorted(
    pageId: Long,
    desktopSize: Size?,
    dragWidget: DesktopWidgetModel?
): List<DesktopWidgetModel>? {
    if (desktopSize == null || dragWidget == null || dragWidget.pageId != pageId) {
        return this
    }
    val columnCount = desktopSize.width.toInt()
    val rowCount = desktopSize.height.toInt()

    val desktopWidgets = this.filter { it.id != dragWidget.id }
    val (insideDesktopWidget, outsideDesktopWidget) = desktopWidgets
        .splitInOutSide(columnCount, rowCount)

    val allInsideWidgets = insideDesktopWidget + dragWidget

    if (!isOccupiedSpace(dragWidget, insideDesktopWidget)) {
        return allInsideWidgets + outsideDesktopWidget
    }
    val sortedHorizontalWidgets = sortHorizontal(columnCount, dragWidget, allInsideWidgets)
    if (sortedHorizontalWidgets != null) {
        return sortedHorizontalWidgets + outsideDesktopWidget
    }
    val sortedVerticalWidgets = sortVertical(rowCount, dragWidget, allInsideWidgets)
    if (sortedVerticalWidgets != null) {
        return sortedVerticalWidgets + outsideDesktopWidget
    }
    return null
}

private fun List<DesktopWidgetModel>.splitInOutSide(
    columnCount: Int,
    rowCount: Int
): Pair<List<DesktopWidgetModel>, List<DesktopWidgetModel>> {
    val desktopBounds = Rect(0, 0, columnCount, rowCount)
    val insideWidgets = mutableListOf<DesktopWidgetModel>()
    this.forEach { widget ->
        if (desktopBounds.contains(widget.bounds)) {
            insideWidgets.add(widget)
        } else if (desktopBounds.isIntersect(widget.bounds)) {
            val insideBounds = widget.bounds.copy(
                right = min(widget.bounds.right, columnCount),
                bottom = min(widget.bounds.bottom, rowCount)
            )
            insideWidgets.add(widget.copy(bounds = insideBounds))
        }
    }
    val outsideWidgets = filter { widget ->
        insideWidgets.all { insideWidget ->
            insideWidget.id != widget.id
        }
    }
    return insideWidgets to outsideWidgets
}

private fun isOccupiedSpace(
    dragWidget: DesktopWidgetModel,
    widgets: List<DesktopWidgetModel>
): Boolean = widgets.any { it.bounds.isIntersect(dragWidget.bounds) }

private fun sortHorizontal(
    columnCount: Int,
    dragWidget: DesktopWidgetModel,
    widgets: List<DesktopWidgetModel>,
): List<DesktopWidgetModel>? {
    var moves = moveHorizontal(columnCount, dragWidget, widgets)
    if (moves == null) {
        val (min, max) = getMinMaxIntersectedDistance(
            widgets,
            dragWidget,
            columnCount,
            isVertical = false
        )
        val minPreMoves = listOf(Move(dragWidget.id, horizontal = min))
        moves = moveHorizontal(columnCount, dragWidget, widgets, minPreMoves)
        if (moves == null) {
            val maxPreMoves = listOf(Move(dragWidget.id, horizontal = max))
            moves = moveHorizontal(columnCount, dragWidget, widgets, maxPreMoves)
        }
    }
    return moves?.let { applyMoves(widgets, moves) }
}

private fun moveHorizontal(
    columnCount: Int,
    moveWidget: DesktopWidgetModel,
    widgets: List<DesktopWidgetModel>,
    preMoves: List<Move> = emptyList(),
    steps: Int = 0
): List<Move>? {
    val preStepMove = preMoves.find { it.widgetId == moveWidget.id }
    if (hasNotSameDirection(steps, preStepMove?.horizontal)) return null
    val stepMove = when (preStepMove) {
        null -> Move(moveWidget.id, horizontal = steps)
        else -> preStepMove.copy(horizontal = preStepMove.horizontal + steps)
    }

    var currentMoves = preMoves.filter { it != preStepMove } + stepMove
    val movedBounds = moveWidget.getMovedBounds(stepMove)
    if (movedBounds.left < 0 || movedBounds.right > columnCount) return null

    for (widget in widgets) {
        if (widget.isDragging || widget.id == moveWidget.id) continue
        val widgetMove = currentMoves.find { it.widgetId == widget.id }
        val widgetBounds = widget.getMovedBounds(widgetMove)
        if (!widgetBounds.isIntersect(movedBounds)) continue

        val rightSteps = movedBounds.right - widgetBounds.left
        val leftSteps = movedBounds.left - widgetBounds.right
        val minSteps = if (abs(rightSteps) <= abs(leftSteps)) rightSteps else leftSteps
        val maxSteps = if (abs(rightSteps) > abs(leftSteps)) rightSteps else leftSteps

        val newSteps = when {
            steps == 0 -> minSteps
            steps > 0 -> rightSteps
            else -> leftSteps
        }
        var newMoves = moveHorizontal(columnCount, widget, widgets, currentMoves, newSteps)
        if (newMoves == null && steps == 0) {
            newMoves = moveHorizontal(columnCount, widget, widgets, currentMoves, maxSteps)
        }
        currentMoves = newMoves ?: return null
    }
    return currentMoves
}

private fun sortVertical(
    rowCount: Int,
    dragWidget: DesktopWidgetModel,
    widgets: List<DesktopWidgetModel>,
): List<DesktopWidgetModel>? {
    var moves = moveVertical(rowCount, dragWidget, widgets)
    if (moves == null) {
        val (min, max) = getMinMaxIntersectedDistance(
            widgets,
            dragWidget,
            rowCount,
            isVertical = true
        )
        val minPreMoves = listOf(Move(dragWidget.id, vertical = min))
        moves = moveVertical(rowCount, dragWidget, widgets, minPreMoves)
        if (moves == null) {
            val maxPreMoves = listOf(Move(dragWidget.id, vertical = max))
            moves = moveVertical(rowCount, dragWidget, widgets, maxPreMoves)
        }
    }
    return moves?.let { applyMoves(widgets, moves) }
}

private fun moveVertical(
    rowCount: Int,
    moveWidget: DesktopWidgetModel,
    widgets: List<DesktopWidgetModel>,
    preMoves: List<Move> = emptyList(),
    steps: Int = 0
): List<Move>? {
    val preStepMove = preMoves.find { it.widgetId == moveWidget.id }
    if (hasNotSameDirection(steps, preStepMove?.vertical)) return null
    val stepMove = when (preStepMove) {
        null -> Move(moveWidget.id, vertical = steps)
        else -> preStepMove.copy(vertical = preStepMove.vertical + steps)
    }

    var currentMoves = preMoves.filter { it != preStepMove } + stepMove
    val movedBounds = moveWidget.getMovedBounds(stepMove)
    if (movedBounds.top < 0 || movedBounds.bottom > rowCount) return null

    for (widget in widgets) {
        if (widget.isDragging || widget.id == moveWidget.id) continue
        val widgetMove = currentMoves.find { it.widgetId == widget.id }
        val widgetBounds = widget.getMovedBounds(widgetMove)
        if (!widgetBounds.isIntersect(movedBounds)) continue

        val bottomSteps = movedBounds.bottom - widgetBounds.top
        val topSteps = movedBounds.top - widgetBounds.bottom

        val minSteps = if (abs(bottomSteps) <= abs(topSteps)) bottomSteps else topSteps
        val maxSteps = if (abs(bottomSteps) > abs(topSteps)) bottomSteps else topSteps

        val newSteps = when {
            steps == 0 -> minSteps
            steps > 0 -> bottomSteps
            else -> topSteps
        }
        var newMoves = moveVertical(rowCount, widget, widgets, currentMoves, newSteps)
        if (newMoves == null && steps == 0) {
            newMoves = moveVertical(rowCount, widget, widgets, currentMoves, maxSteps)
        }
        currentMoves = newMoves ?: return null
    }
    return currentMoves
}

private fun applyMoves(
    widgets: List<DesktopWidgetModel>,
    moves: List<Move>
): List<DesktopWidgetModel> = mutableListOf<DesktopWidgetModel>().apply {
    for (widget in widgets) {
        val move = moves.find { it.widgetId == widget.id }
        val sortedWidget = when (move?.hasOffset) {
            true -> widget.copy(
                bounds = widget.bounds.copyOffset(move.horizontal, move.vertical)
            )
            else -> widget
        }
        add(sortedWidget)
    }
}

private fun hasNotSameDirection(first: Int?, second: Int?) = when {
    first == null || first == 0 -> false
    second == null || second == 0 -> false
    else -> first.sign != second.sign
}

private fun getMinMaxIntersectedDistance(
    widgets: List<DesktopWidgetModel>,
    widget: DesktopWidgetModel,
    availableCount: Int,
    isVertical: Boolean
): Pair<Int, Int> {
    var min = 0
    var max = 0
    widgets.forEach {
        if (it.bounds.isIntersect(widget.bounds)) {
            val bottomDiff = when (isVertical) {
                true -> it.bounds.top - widget.bounds.bottom
                false -> it.bounds.left - widget.bounds.right
            }
            val topDiff = when (isVertical) {
                true -> it.bounds.bottom - widget.bounds.top
                false -> it.bounds.right - widget.bounds.left
            }
            val minLocal = if (abs(bottomDiff) <= abs(topDiff)) bottomDiff else topDiff
            val maxLocal = if (abs(bottomDiff) >= abs(topDiff)) bottomDiff else topDiff

            min = if (min == 0 || abs(minLocal) < abs(min)) minLocal else min
            max = if (max == 0 || abs(maxLocal) > abs(max)) maxLocal else max
        }
    }

    if (isVertical && widget.bounds.top + min < 0) {
        min = -widget.bounds.top
    } else if (!isVertical && widget.bounds.left + min < 0) {
        min = -widget.bounds.left
    }

    if (isVertical && widget.bounds.top + max < 0) {
        max = -widget.bounds.top
    } else if (!isVertical && widget.bounds.left + max < 0) {
        max = -widget.bounds.left
    }

    if (isVertical && widget.bounds.bottom + min > availableCount) {
        min = availableCount - widget.bounds.bottom
    } else if (!isVertical && widget.bounds.right + min > availableCount) {
        min = availableCount - widget.bounds.right
    }

    if (isVertical && widget.bounds.bottom + max > availableCount) {
        max = availableCount - widget.bounds.bottom
    } else if (!isVertical && widget.bounds.right + max > availableCount) {
        max = availableCount - widget.bounds.right
    }
    return min to max
}

private data class Move(
    val widgetId: String,
    val vertical: Int = 0,
    val horizontal: Int = 0
)

private fun DesktopWidgetModel.getMovedBounds(move: Move?) = when (move) {
    null -> bounds
    else -> bounds.copyOffset(move.horizontal, move.vertical)
}

private val Move.hasOffset get() = vertical != 0 || horizontal != 0
