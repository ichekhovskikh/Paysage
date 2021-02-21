package com.chekh.paysage.core.ui.view.recycler.diffable

interface ListItem {

    val id: String

    val layout: Int

    fun bind(holder: ListItemAdapter.ListViewHolder, payloads: List<Any>? = null) {}

    fun isSameAs(another: ListItem): Boolean =
        this::class == another::class &&
            this.layout == another.layout &&
            this.id == another.id

    fun hasSameContentsAs(another: ListItem): Boolean =
        this == another

    fun getChangePayload(another: ListItem): Any? = null

}
