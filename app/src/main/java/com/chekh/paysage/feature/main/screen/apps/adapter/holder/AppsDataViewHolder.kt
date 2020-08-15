package com.chekh.paysage.feature.main.screen.apps.adapter.holder

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.screen.apps.model.ExpandableAppsGroupByCategoryModel
import com.chekh.paysage.feature.main.screen.apps.view.AppsDataView

class AppsDataViewHolder(
    private val view: AppsDataView,
    private val onScrollChange: (Int, String) -> Unit,
    private val onScrollStateChange: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.apply {
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            val padding = resources.getDimension(R.dimen.small).toInt()
            setPadding(padding, padding, padding, padding)
            setScrollStateChangeListener { onScrollStateChange(it) }
        }
    }

    fun bind(appCategory: ExpandableAppsGroupByCategoryModel) {
        view.isExpanded = appCategory.isExpanded
        setApps(appCategory.data.apps, appCategory.data.appSettings)
        view.scrollOffset = appCategory.scrollOffset

        view.setOffsetChangeListener { offset ->
            val category = appCategory.data.category
            onScrollChange(offset, category.id)
        }
    }

    fun setApps(apps: List<AppModel>, appSettings: AppSettingsModel) {
        view.spanCount = appSettings.appSpan
        view.appSize = appSettings.appSize
        view.setApps(apps)
    }

    fun expand(isExpanded: Boolean) {
        view.animatedExpand(isExpanded)
    }

    fun setScrollOffset(scrollOffset: Int) {
        view.scrollOffset = scrollOffset
    }
}