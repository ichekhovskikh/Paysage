package com.chekh.paysage.ui.view.core.stickyheader

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.chekh.paysage.extension.absoluteHeight
import com.chekh.paysage.extension.setMarginTop
import com.chekh.paysage.ui.util.MetricsConvector
import kotlinx.android.extensions.LayoutContainer

abstract class StickyAdapter<HVH : RecyclerView.ViewHolder, CVH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<StickyAdapter.StickyViewHolder<HVH, CVH>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyViewHolder<HVH, CVH> {
        val headerHolder = onCreateHeaderViewHolder(parent, viewType)
        val dataHolder = onCreateContentViewHolder(parent, viewType)
        return StickyViewHolder(parent, headerHolder, dataHolder)
    }

    override fun onBindViewHolder(holder: StickyViewHolder<HVH, CVH>, position: Int) {
        onBind(holder.itemView as ViewGroup, holder.header, holder.content, position)
    }

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): HVH

    abstract fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): CVH

    abstract fun onBind(parent: ViewGroup, headerHolder: HVH, contentHolder: CVH, position: Int)

    class StickyViewHolder<HVH : RecyclerView.ViewHolder, CVH : RecyclerView.ViewHolder>(
        parent: ViewGroup,
        val header: HVH,
        val content: CVH
    ) : RecyclerView.ViewHolder(FrameLayout(parent.context)), LayoutContainer {

        override val containerView: ViewGroup = itemView as ViewGroup

        init {
            containerView.apply {
                val marginLayoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                marginLayoutParams.bottomMargin = MetricsConvector.convertDpToPx(8f)
                layoutParams = marginLayoutParams
                header.itemView.measure(WRAP_CONTENT, WRAP_CONTENT)
                content.itemView.setMarginTop(header.itemView.absoluteHeight)
                addView(content.itemView)
                addView(header.itemView)
                tag = this@StickyViewHolder
            }
        }
    }
}