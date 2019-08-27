package com.chekh.paysage.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.ui.view.app.AppView

class AppsListAdapter : RecyclerView.Adapter<AppsListAdapter.AppViewHolder>() {

    private lateinit var apps: List<AppInfo>

    fun setApps(apps: List<AppInfo>) {
        this.apps = apps
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.setApp(apps[position])
    }

    override fun getItemCount(): Int {
        return if (::apps.isInitialized) apps.size else 0
    }

    class AppViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(AppView(parent.context)) {
        private val appView = itemView as AppView

        init {
            appView.isHideTitle = true
        }

        fun setApp(app: AppInfo) {
            appView.setAppInfo(app)
        }
    }
}