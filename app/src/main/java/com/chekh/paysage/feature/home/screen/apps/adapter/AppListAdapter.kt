package com.chekh.paysage.feature.home.screen.apps.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.home.domain.model.AppModel
import com.chekh.paysage.ui.view.AppView

class AppListAdapter : RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

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
        holder.setApp(apps[position])
    }

    override fun getItemCount() = apps.size

    class AppViewHolder(private val appView: AppView) : RecyclerView.ViewHolder(appView) {

        init {
            appView.apply {
                layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                val padding = resources.getDimension(R.dimen.micro).toInt()
                setPadding(padding, padding, padding, padding)
                isLabelVisible = false
            }
        }

        fun setApp(app: AppModel) {
            // TODO make SettingsService for iconSize
            //TODO make BadgerDrawable
            appView.icon = app.icon
            appView.label = app.title
            appView.setOnClickListener {
                // startApp(app)
            }
        }
    }
}