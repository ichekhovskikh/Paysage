package com.chekh.paysage.feature.main.presentation.apps.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.presentation.apps.adapter.AppAdapter
import kotlin.math.max

class DockBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val gridLayoutManager: GridLayoutManager
    private val adapter = AppAdapter()

    init {
        setAdapter(adapter)
        setBackgroundResource(R.drawable.bg_grey_rounded)
        gridLayoutManager = GridLayoutManager(context, MIN_APPS_COUNT)
        layoutManager = gridLayoutManager
        overScrollMode = OVER_SCROLL_NEVER
    }

    fun setAppSettings(settings: AppSettingsModel) {
        adapter.appSize = settings.appSize
        if (adapter.itemCount > 0) {
            adapter.notifyDataSetChanged()
        }
    }

    fun setApps(dockApps: List<AppModel>) {
        gridLayoutManager.spanCount = max(MIN_APPS_COUNT, dockApps.size)
        adapter.setApps(dockApps)
    }

    companion object {
        const val MIN_APPS_COUNT = 1
    }
}
