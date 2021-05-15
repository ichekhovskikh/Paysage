package com.chekh.paysage.feature.widget.presentation.widgetboard

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.*
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.DesktopInsetsViewModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.WidgetGroupAdapter
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.toClipData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_widget_board.*

@AndroidEntryPoint
class WidgetBoardFragment : BaseFragment(R.layout.fragment_widget_board), BottomSheetCallback {

    private val viewModel: WidgetBoardViewModel by viewModels()
    private val insetsViewModel: DesktopInsetsViewModel by activityViewModels()

    private val adapter: WidgetGroupAdapter by lazy {
        WidgetGroupAdapter(viewModel::onGroupScrollOffsetChanged, ::startDragAndDrop)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.init(Unit)

        insetsViewModel.windowInsetsLiveData.observe(viewLifecycleOwner, ::onApplyWindowInsets)
        viewModel.widgetGroupsLiveData.observe(viewLifecycleOwner) { widgetGroups ->
            val isAnimate = adapter.itemCount == 0
            adapter.setWidgetGroups(widgetGroups, isAnimate)
        }
    }

    @Suppress("DEPRECATION")
    private fun onApplyWindowInsets(insets: WindowInsets) {
        bsrvWidgetPackages.topMargin = insets.systemWindowInsetTop
        bsrvWidgetPackages.bottomMargin = insets.systemWindowInsetBottom
    }

    private fun setupListView() {
        bsrvWidgetPackages.adapter = adapter
    }

    private fun startDragAndDrop(view: View, widget: WidgetModel) {
        val activity = activity as? DesktopActivity ?: return
        activity.startDragAndDrop(view, data = widget.toClipData())
        exit()
    }
}
