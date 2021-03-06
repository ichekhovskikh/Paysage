package com.chekh.paysage.core.ui.view.stickyheader

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.absoluteHeight
import com.chekh.paysage.core.extension.setMarginTop

abstract class StickyAdapter<T, HVH : RecyclerView.ViewHolder, CVH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, StickyAdapter.StickyViewHolder<HVH, CVH>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyViewHolder<HVH, CVH> {
        val headerHolder = onCreateHeaderViewHolder(parent, viewType)
        val dataHolder = onCreateContentViewHolder(parent, viewType)
        return StickyViewHolder(FrameLayout(parent.context), headerHolder, dataHolder)
    }

    override fun onBindViewHolder(
        holder: StickyViewHolder<HVH, CVH>,
        position: Int
    ) {
        val parent = holder.itemView as ViewGroup
        onBindHeaderViewHolder(parent, holder.header, position, null)
        onBindContentViewHolder(parent, holder.content, position, null)
    }

    override fun onBindViewHolder(
        holder: StickyViewHolder<HVH, CVH>,
        position: Int,
        payloads: List<Any>
    ) {
        val parent = holder.itemView as ViewGroup
        onBindHeaderViewHolder(parent, holder.header, position, payloads)
        onBindContentViewHolder(parent, holder.content, position, payloads)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): HVH

    abstract fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): CVH

    abstract fun onBindHeaderViewHolder(
        parent: ViewGroup,
        headerHolder: HVH,
        position: Int,
        payloads: List<Any>?
    )

    abstract fun onBindContentViewHolder(
        parent: ViewGroup,
        contentHolder: CVH,
        position: Int,
        payloads: List<Any>?
    )

    class StickyViewHolder<HVH : RecyclerView.ViewHolder, CVH : RecyclerView.ViewHolder>(
        containerView: ViewGroup,
        val header: HVH,
        val content: CVH
    ) : RecyclerView.ViewHolder(containerView) {

        init {
            containerView.apply {
                addView(content.itemView)
                addView(header.itemView)

                val marginLayoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                marginLayoutParams.bottomMargin = resources.getDimension(R.dimen.small).toInt()
                layoutParams = marginLayoutParams

                header.itemView.measure(WRAP_CONTENT, WRAP_CONTENT)
                content.itemView.setMarginTop(header.itemView.absoluteHeight)
            }
        }
    }
}
