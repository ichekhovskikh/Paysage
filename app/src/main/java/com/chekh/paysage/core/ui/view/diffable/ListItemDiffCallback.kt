package com.chekh.paysage.core.ui.view.diffable

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class ListItemDiffCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = newItem.isSameAs(oldItem)

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = newItem.hasSameContentsAs(oldItem)

    override fun getChangePayload(oldItem: T, newItem: T) = newItem.getChangePayload(oldItem)

}
