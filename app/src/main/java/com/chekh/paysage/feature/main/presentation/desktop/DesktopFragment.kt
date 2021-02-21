package com.chekh.paysage.feature.main.presentation.desktop

import android.app.Activity
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.core.graphics.toRectF
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.core.ui.view.flow.FlowListAdapter
import com.chekh.paysage.feature.main.presentation.MainActivity
import com.chekh.paysage.feature.main.presentation.desktop.adapter.DesktopWidgetFlowListItem
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager.Companion.WIDGET_ATTACH_REQUEST_CODE
import com.chekh.paysage.feature.main.presentation.desktop.tools.getWidgetBounds
import com.chekh.paysage.feature.main.presentation.home.HomeViewModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_apps.*
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
        flWidgets.adapter = adapter
    }

    private fun setupListeners() {
        val activity = activity as? MainActivity
        activity?.addDragAndDropListener(this)
        flWidgets.setOnGestureScaleAndLongPress {
            homeViewModel.isEnabledOverlayHomeButtonsLiveData.postValue(true)
        }
        adapter.setOnItemsCommittedListener { items ->
            val draggingItem = items.find { it is DesktopWidgetFlowListItem && it.isDragging }
            draggingItem ?: return@setOnItemsCommittedListener
            val bounds = flWidgets.getItemBounds(draggingItem)
            bounds.offset(flWidgets.startMargin, flWidgets.topMargin)
            activity?.setTargetDragViewBounds(bounds.toRectF())
        }
    }

    private fun setupViewModel() {
        desktopViewModel.init(Unit)

        desktopViewModel.desktopGridSizeLiveData.observe(viewLifecycleOwner) { (columns, rows) ->
            flWidgets.setSize(columns.toInt(), rows.toInt())
        }
        desktopViewModel.desktopWidgetsLiveData.observe(viewLifecycleOwner) { items ->
            adapter.items = items
        }
        desktopViewModel.dockAppSizeLiveData.observe(viewLifecycleOwner) { appSize ->
            flWidgets.applyPadding(bottom = appSize)
        }
    }

    @Suppress("DEPRECATION")
    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val decoratedMargin = resources.getDimension(R.dimen.small).toInt()
        flWidgets.bottomMargin = insets.systemWindowInsetBottom + decoratedMargin
        flWidgets.topMargin = insets.systemWindowInsetTop + decoratedMargin
    }

    override fun onDragStart(location: RectF, data: ClipData?) {
        onDragMove(location, data)
    }

    override fun onDragMove(location: RectF, data: ClipData?) {
        when (data) {
            is WidgetClipData -> {
                val widget = data.widget
                val bounds = widget.getWidgetBounds(location)
                bounds.offset(-flWidgets.startMargin.toFloat(), -flWidgets.topMargin.toFloat())
                val cells = flWidgets.getOccupiedCells(bounds)
                desktopViewModel.setDraggingDesktopWidget(null, widget, cells)
            }
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
        val bounds = widget.getWidgetBounds(location)
        bounds.offset(-flWidgets.startMargin.toFloat(), -flWidgets.topMargin.toFloat())
        val cells = flWidgets.getOccupiedCells(bounds)
        if (hasWidgetAttachPermissions) {
            desktopViewModel.addDesktopWidget(widgetId, widget, cells)
            return
        }
        registerActivityResultListener(WIDGET_ATTACH_REQUEST_CODE) { resultCode, _ ->
            when (resultCode) {
                Activity.RESULT_OK -> desktopViewModel.addDesktopWidget(widgetId, widget, cells)
                else -> desktopViewModel.clearDraggingDesktopWidget()
            }
        }
    }
}
