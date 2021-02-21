package com.chekh.paysage.core.ui.view.flow

import com.chekh.paysage.core.ui.view.flow.items.FlowListItem
import com.chekh.paysage.core.ui.view.recycler.diffable.ListItemAdapter

internal interface FlowAdapterObserver {

    fun onCurrentListChanged(
        previousList: List<FlowListItem>,
        currentList: List<FlowListItem>
    )
}

class FlowListAdapter : ListItemAdapter<FlowListItem>() {

    private val observers = mutableListOf<FlowAdapterObserver>()

    internal fun registerAdapterObserver(adapterObserver: FlowAdapterObserver) {
        observers.add(adapterObserver)
    }

    internal fun unregisterAdapterObserver(adapterObserver: FlowAdapterObserver) {
        observers.remove(adapterObserver)
    }

    override fun onCurrentListChanged(
        previousList: List<FlowListItem>,
        currentList: List<FlowListItem>
    ) {
        observers.forEach { it.onCurrentListChanged(previousList, currentList) }
    }
}
