package com.chekh.paysage.feature.widget.presentation.widgetboard

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.*
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.WidgetsPackageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_widget_board.*

@AndroidEntryPoint
class WidgetBoardFragment : BaseFragment(R.layout.fragment_widget_board), BottomSheetCallback {

    private val viewModel: WidgetBoardViewModel by viewModels()

    private val adapter: WidgetsPackageAdapter by lazy {
        WidgetsPackageAdapter(viewModel::scrollCategoryOffset)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.init(Unit)

        viewModel.widgetsGroupByPackageLiveData.observe(viewLifecycleOwner) { widgetsGroupByPackage ->
            val isAnimate = adapter.itemCount == 0
            adapter.setWidgetPackages(widgetsGroupByPackage, isAnimate)
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        bsrvWidgetPackages.setMarginTop(insets.systemWindowInsetTop)
        bsrvWidgetPackages.setMarginBottom(insets.systemWindowInsetBottom)
    }

    private fun setupListView() {
        bsrvWidgetPackages.adapter = adapter
    }
}
