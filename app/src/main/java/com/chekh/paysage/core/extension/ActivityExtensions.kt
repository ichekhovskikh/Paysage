package com.chekh.paysage.core.extension

import android.app.Activity
import android.view.ViewGroup

val Activity.contentView: ViewGroup? get() = findViewById(android.R.id.content)
