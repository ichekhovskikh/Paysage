package com.chekh.paysage.feature.main.presentation.desktop

import android.app.Activity
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toRectF
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.setOnGestureScaleAndLongPress
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.core.ui.view.flow.FlowLayoutManager
import com.chekh.paysage.core.ui.view.flow.FlowListAdapter
import com.chekh.paysage.core.ui.view.flow.items.FlowListItem
import com.chekh.paysage.feature.main.presentation.MainActivity
import com.chekh.paysage.feature.main.presentation.desktop.adapter.DesktopWidgetFlowListItem
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopItemAnimator
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager.Companion.WIDGET_ATTACH_REQUEST_CODE
import com.chekh.paysage.feature.main.presentation.home.HomeViewModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
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

    private val layoutManager get() = rvWidgets.layoutManager as FlowLayoutManager

    private val maxAvailableItemWidth: Int
        get() = layoutManager.width - layoutManager.paddingStart - layoutManager.paddingEnd

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupListeners()
        setupViewModel()
    }

    private fun setupListView() {
        rvWidgets.adapter = adapter
        rvWidgets.itemAnimator = DesktopItemAnimator()
    }

    private fun setupListeners() {
        val activity = activity as? MainActivity
        activity?.addDragAndDropListener(this)
        rvWidgets.setOnGestureScaleAndLongPress {
            homeViewModel.isEnabledOverlayHomeButtonsLiveData.postValue(true)
        }
        adapter.setOnItemsCommittedListener {
            val item = it.getDraggingItem() ?: return@setOnItemsCommittedListener
            val bounds = layoutManager.getItemBounds(item)
                .apply { offset(0, -layoutManager.verticalOffset) }
                .toRectF()
            activity?.setTargetDragViewBounds(bounds)
        }
    }

    private fun setupViewModel() {
        desktopViewModel.init(Unit)

        rvWidgets.post { desktopViewModel.setMaxWidgetWidth(maxAvailableItemWidth) }
        desktopViewModel.desktopGridSpanLiveData.observe(viewLifecycleOwner) { spanCount ->
            layoutManager.spanCount = spanCount
        }
        desktopViewModel.desktopWidgetsLiveData.observe(viewLifecycleOwner) { items ->
            adapter.items = items
        }
    }

    override fun onDragStart(location: RectF, data: ClipData?) {
        onDragMove(location, data)
    }

    override fun onDragMove(location: RectF, data: ClipData?) {
        location.offset(0f, layoutManager.verticalOffset.toFloat())
        when (data) {
            is WidgetClipData -> {
                desktopViewModel.setDraggingDesktopWidget(null, data.widget, location)
            }
        }
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        location.offset(0f, layoutManager.verticalOffset.toFloat())
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
            return
        }
        registerActivityResultListener(WIDGET_ATTACH_REQUEST_CODE) { resultCode, _ ->
            when (resultCode) {
                Activity.RESULT_OK -> desktopViewModel.addDesktopWidget(widgetId, widget, location)
                else -> desktopViewModel.clearDraggingDesktopWidget()
            }
        }
    }

    private fun List<FlowListItem>.getDraggingItem() =
        find { it is DesktopWidgetFlowListItem && it.isDragging }
}
