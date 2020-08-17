package com.chekh.paysage.feature.main.screen.apps.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.ListAdapter
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.screen.apps.adapter.differ.AppDiffCallback
import com.chekh.paysage.feature.main.screen.apps.adapter.holder.AppViewHolder
import com.chekh.paysage.ui.view.AppView

class AppListAdapter : ListAdapter<AppModel, AppViewHolder>(AppDiffCallback()) {

    var appSize: Int = WRAP_CONTENT

    fun setApps(apps: List<AppModel>) {
        submitList(apps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppViewHolder(AppView(parent.context))

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setAppSize(appSize)
    }
}