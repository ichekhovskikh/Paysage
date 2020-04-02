package com.chekh.paysage.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chekh.paysage.R
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.ui.util.MetricsConverter

class AppView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val iconView: ImageView
    private val titleView: TextView

    var info: AppInfo? = null
        private set

    init {
        LayoutInflater.from(context).inflate(R.layout.view_app, this)
        layoutParams = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        orientation = VERTICAL
        val padding = MetricsConverter(context).dpToPx(6f)
        setPadding(padding, padding, padding, padding)
        iconView = findViewById(R.id.icon)
        titleView = findViewById(R.id.title)
    }

    var isHideTitle: Boolean = false
        set(value) {
            field = value
            titleView.visibility = if (value) GONE else View.VISIBLE
        }

    fun setAppInfo(info: AppInfo) {
        this.info = info
        iconView.setImageBitmap(info.icon) //TODO make BadgerDrawable
        titleView.text = info.title
        setOnClickListener { startApp() }
    }

    fun startApp() {

    }
}