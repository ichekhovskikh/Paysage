package com.chekh.paysage.feature.main.presentation.desktop

import android.graphics.RectF
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.setOnGestureScaleAndLongPress
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.core.ui.view.flow.FlowLayoutManager
import com.chekh.paysage.core.ui.view.flow.FlowListAdapter
import com.chekh.paysage.feature.main.presentation.MainActivity
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager.Companion.WIDGET_ATTACH_REQUEST_CODE
import com.chekh.paysage.feature.main.presentation.home.HomeViewModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.data.WidgetClipData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_desktop.*
import javax.inject.Inject

@AndroidEntryPoint
class DesktopFragment :
    BaseFragment(R.layout.fragment_desktop),
    DragAndDropListener {

    private val desktopViewModel: DesktopViewModel by viewModels()

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    @Inject
    lateinit var widgetHostManager: DesktopWidgetHostManager

    private val adapter by lazy { FlowListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupListeners()
        setupViewModel()
    }

    private fun setupListView() {
        rvWidgets.adapter = adapter
    }

    private fun setupListeners() {
        val activity = activity as? MainActivity
        activity?.addDragAndDropListener(this)
        rvWidgets.setOnGestureScaleAndLongPress {
            homeViewModel.isEnabledOverlayHomeButtonsLiveData.postValue(true)
        }
    }

    private fun setupViewModel() {
        desktopViewModel.init(Unit)

        desktopViewModel.desktopGridSpan.observe(viewLifecycleOwner) { spanCount ->
            val layoutManager = rvWidgets.layoutManager as FlowLayoutManager
            layoutManager.spanCount = spanCount
        }
        desktopViewModel.desktopWidgets.observe(viewLifecycleOwner) { items ->
            adapter.items = items
        }
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        when (data) {
            is WidgetClipData -> {
                addDesktopWidget(location, data.widget)
            }
        }
    }

    private fun addDesktopWidget(location: RectF, widget: WidgetModel) {
        val widgetId = widgetHostManager.allocateDesktopWidgetId()
        val hasWidgetAttachPermissions = widgetHostManager.requestWidgetAttachPermissionsIfNeed(
            this,
            widgetId,
            widget.packageName,
            widget.className
        )
        if (hasWidgetAttachPermissions) {
            desktopViewModel.addDesktopWidget(widgetId, widget, location)
        } else {
            registerActivityResultListener(WIDGET_ATTACH_REQUEST_CODE) {
                desktopViewModel.addDesktopWidget(widgetId, widget, location)
            }
        }
    }
}
