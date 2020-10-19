package com.chekh.paysage.feature.main.presentation.apps.adapter.holder

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.presentation.apps.model.ExpandableAppsGroupByCategoryModel
import com.chekh.paysage.feature.main.presentation.apps.view.AppsDataView

class AppsDataViewHolder(
    private val view: AppsDataView,
    private val onScrollChange: (Int, String) -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.apply {
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            val padding = resources.getDimension(R.dimen.small).toInt()
            setPadding(padding, padding, padding, padding)
        }
    }

    fun bind(appCategory: ExpandableAppsGroupByCategoryModel) {
        view.expand(appCategory.isExpanded)
        setApps(
            appCategory.data.apps,
            appCategory.data.settings,
            isAnimate = false,
            onUpdated = { view.scrollOffset = appCategory.scrollOffset }
        )
        view.setOffsetChangeListener { offset ->
            val category = appCategory.data.category
            onScrollChange(offset, category.id)
        }
    }

    fun setApps(
        apps: List<AppModel>,
        appSettings: AppSettingsModel,
        isAnimate: Boolean = true,
        onUpdated: (() -> Unit)? = null
    ) {
        view.spanCount = appSettings.appSpan
        view.appSize = appSettings.appSize
        view.setApps(apps, isAnimate, onUpdated)
    }

    fun expand(isExpanded: Boolean) {
        view.expand(isExpanded, isAnimate = true)
    }

    fun setScrollOffset(scrollOffset: Int) {
        view.scrollOffset = scrollOffset
    }
}
