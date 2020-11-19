package com.chekh.paysage.feature.main.presentation.apps.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.core.ui.view.AppView
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.presentation.apps.adapter.differ.AppDiffCallback
import com.chekh.paysage.feature.main.presentation.apps.adapter.holder.AppViewHolder

class AppAdapter : ListAdapter<AppModel, AppViewHolder>(AppDiffCallback()) {

    private var recycler: RecyclerView? = null
    private var itemAnimator: RecyclerView.ItemAnimator? = null

    var appSize: Int = WRAP_CONTENT

    fun setApps(
        apps: List<AppModel>,
        isAnimate: Boolean = true,
        onUpdated: (() -> Unit)? = null
    ) {
        setAnimate(isAnimate)
        submitList(apps, onUpdated)
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        super.onAttachedToRecyclerView(recycler)
        this.recycler = recycler
        this.itemAnimator = recycler.itemAnimator
    }

    private fun setAnimate(isAnimate: Boolean) {
        recycler?.itemAnimator = if (isAnimate) itemAnimator else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppViewHolder(AppView(parent.context))

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setAppSize(appSize)
    }
}
