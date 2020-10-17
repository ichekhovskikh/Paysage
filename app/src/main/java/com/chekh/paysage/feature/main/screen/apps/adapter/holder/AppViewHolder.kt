package com.chekh.paysage.feature.main.screen.apps.adapter.holder

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.view.AppView
import com.chekh.paysage.feature.main.domain.model.AppModel

class AppViewHolder(
    private val appView: AppView
) : RecyclerView.ViewHolder(appView) {

    init {
        appView.apply {
            layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val padding = resources.getDimension(R.dimen.micro).toInt()
            setPadding(padding, padding, padding, padding)
            isLabelVisible = false
        }
    }

    fun bind(app: AppModel) {
        // TODO make SettingsService for iconSize
        // TODO make BadgerDrawable
        appView.setIcon(app.icon)
        appView.label = app.title
        appView.setOnClickListener {
            // startApp(app)
        }
    }

    fun setAppSize(appSize: Int) {
        val layoutParams = appView.layoutParams as ViewGroup.MarginLayoutParams
        if (layoutParams.width != appSize && layoutParams.height != appSize) {
            layoutParams.width = appSize
            layoutParams.height = appSize
            appView.layoutParams = layoutParams
        }
    }
}
