package com.chekh.paysage.handler

import android.view.View
import androidx.core.view.marginTop

class SearchBarSlideHandler(private val searchBar: View) {

    fun slideFromTop(value: Float) {
        searchBar.alpha = value
        val height = searchBar.measuredHeight
        searchBar.y = (value) * height - height + searchBar.marginTop
        searchBar.visibility  = if (value == 0f)  View.GONE else View.VISIBLE
    }
}