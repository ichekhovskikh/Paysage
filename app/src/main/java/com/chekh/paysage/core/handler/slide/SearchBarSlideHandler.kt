package com.chekh.paysage.core.handler.slide

import android.view.View
import androidx.core.view.marginTop

class SearchBarSlideHandler(private val searchBar: View) {

    fun slideToTop(offset: Float, anchor: Float) {
        val value = transformOffset(offset, anchor)
        searchBar.alpha = value
        val height = searchBar.measuredHeight
        searchBar.y = (value) * height - height + searchBar.marginTop
        searchBar.visibility = if (value == 0f) View.GONE else View.VISIBLE
    }

    private fun transformOffset(offset: Float, anchor: Float): Float =
        when (offset > anchor) {
            true -> (1 - offset) / (1 - anchor)
            else -> offset / anchor
        }
}
