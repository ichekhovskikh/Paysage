package com.chekh.paysage.feature.home.screen.apps.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.home.domain.model.AppListModel
import com.chekh.paysage.feature.home.screen.apps.adapter.AppListAdapter
import kotlin.math.max

class DockBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val gridLayoutManager: GridLayoutManager
    private val adapter = AppListAdapter()

    init {
        setAdapter(adapter)
        setBackgroundResource(R.drawable.background_grey_rounded)
        gridLayoutManager = GridLayoutManager(context, MIN_APPS_COUNT)
        layoutManager = gridLayoutManager
        overScrollMode = OVER_SCROLL_NEVER
    }

    fun setApps(appList: AppListModel) {
        val limitedApps = appList.apps.take(MAX_APPS_COUNT)
        gridLayoutManager.spanCount = max(MIN_APPS_COUNT, limitedApps.size)
        adapter.appSize = appList.appSize
        adapter.setApps(limitedApps)
    }

    companion object {
        const val MIN_APPS_COUNT = 1
        const val MAX_APPS_COUNT = 4
    }

}