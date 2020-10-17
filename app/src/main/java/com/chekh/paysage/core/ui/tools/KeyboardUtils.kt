package com.chekh.paysage.core.ui.tools

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val context = context ?: return
    val windowToken = windowToken ?: return
    val inputMethodManager = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}
