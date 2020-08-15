package com.chekh.paysage.feature.main.screen.apps.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.screen.apps.adapter.holder.AppViewHolder
import com.chekh.paysage.ui.view.AppView

class AppListAdapter : RecyclerView.Adapter<AppViewHolder>() {

    var appSize: Int = WRAP_CONTENT

    private var apps: List<AppModel> = listOf()

    fun setApps(apps: List<AppModel>) {
        if (this.apps == apps) return
        this.apps = apps
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(AppView(parent.context))
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(apps[position])
        holder.setAppSize(appSize)
    }

    override fun getItemCount() = apps.size
}