package com.chekh.paysage.feature.main.presentation.desktop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.onClick
import com.chekh.paysage.core.extension.setOnGestureScaleAndLongPress
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.flow.FlowListAdapter
import com.chekh.paysage.feature.main.presentation.desktop.adapter.WidgetFlowListItem
import com.chekh.paysage.feature.main.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_desktop.*

@AndroidEntryPoint
class DesktopFragment : BaseFragment(R.layout.fragment_desktop) {

    private val viewModel: HomeViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private val adapter = FlowListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testFlowLayout()
        setupListeners()
    }

    private fun setupListeners() {
        glWidgets.setOnGestureScaleAndLongPress {
            viewModel.isEnabledOverlayHomeButtonsLiveData.postValue(true)
        }
    }

    private fun testFlowLayout() {
        val first = listOf(
            WidgetFlowListItem("0", 0, 0, 2, 2),
            WidgetFlowListItem("1", 2, 2, 1, 1),
            WidgetFlowListItem("2", 0, 4, 2, 1),
            WidgetFlowListItem("3", 1, 8, 3, 2),
            WidgetFlowListItem("4", 1, 12, 3, 2),
            WidgetFlowListItem("5", 1, 15, 3, 2),
            WidgetFlowListItem("6", 1, 17, 3, 2),
            WidgetFlowListItem("7", 1, 19, 3, 2),
            WidgetFlowListItem("9", 1, 21, 3, 2),
            WidgetFlowListItem("10", 1, 23, 3, 2),
            WidgetFlowListItem("11", 1, 25, 3, 2),
            WidgetFlowListItem("12", 1, 27, 3, 2),
            WidgetFlowListItem("13", 1, 29, 3, 2),
            WidgetFlowListItem("14", 1, 31, 3, 2),
            WidgetFlowListItem("15", 1, 33, 3, 2),
            WidgetFlowListItem("16", 1, 35, 3, 2),
            WidgetFlowListItem("17", 1, 37, 3, 2),
            WidgetFlowListItem("18", 1, 39, 3, 2),
            WidgetFlowListItem("19", 1, 41, 3, 2)
        )
        val second = listOf(
            WidgetFlowListItem("0", 0, 0, 2, 2),
            WidgetFlowListItem("1", 2, 2, 1, 1),
            WidgetFlowListItem("2", 0, 2, 2, 1),
            WidgetFlowListItem("3", 1, 8, 3, 2),
            WidgetFlowListItem("4", 1, 12, 3, 2),
            WidgetFlowListItem("5", 1, 15, 3, 2),
            WidgetFlowListItem("6", 1, 17, 3, 2),
            WidgetFlowListItem("7", 1, 19, 3, 2),
            WidgetFlowListItem("9", 1, 21, 3, 2),
            WidgetFlowListItem("10", 1, 23, 3, 2),
            WidgetFlowListItem("11", 1, 25, 3, 2),
            WidgetFlowListItem("12", 1, 27, 3, 2),
            WidgetFlowListItem("13", 1, 29, 3, 2),
            WidgetFlowListItem("14", 1, 31, 3, 2),
            WidgetFlowListItem("15", 1, 33, 3, 2),
            WidgetFlowListItem("16", 1, 35, 3, 2),
            WidgetFlowListItem("17", 1, 37, 3, 2),
            WidgetFlowListItem("18", 1, 39, 3, 2),
            WidgetFlowListItem("19", 1, 41, 3, 2)
        )
        glWidgets.adapter = adapter.apply {
            items = second
        }
        tvShuffle.onClick {
            adapter.items = if (adapter.items == second) first else second
        }
    }
}
