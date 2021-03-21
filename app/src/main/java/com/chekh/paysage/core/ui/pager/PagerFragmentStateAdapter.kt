package com.chekh.paysage.core.ui.pager

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

private data class FragmentItem(val id: Long, val fragment: Fragment)

class PagerFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var items: MutableList<FragmentItem> = mutableListOf()

    override fun createFragment(position: Int) = items[position].fragment

    override fun getItemId(position: Int) = items[position].id

    override fun getItemCount() = items.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    fun getFragment(id: Long) = items.find { it.id == id }?.fragment

    fun setFragments(fragments: List<Pair<Long, Fragment>>) {
        this.items = fragments
            .map { (id, fragment) -> FragmentItem(id, fragment) }
            .toMutableList()
        notifyDataSetChanged()
    }

    fun addFragment(id: Long, fragment: Fragment, position: Int = -1) {
        if (position < 0) {
            items.add(FragmentItem(id, fragment))
            notifyItemInserted(items.size - 1)
        } else {
            items.add(position, FragmentItem(id, fragment))
            notifyItemInserted(position)
        }
    }

    fun removeFragment(fragment: Fragment) {
        val position = items.indexOfFirst { it.fragment == fragment }
        removeFragmentAt(position)
    }

    fun removeFragmentAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
