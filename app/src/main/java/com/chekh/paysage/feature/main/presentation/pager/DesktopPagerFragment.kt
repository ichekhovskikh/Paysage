package com.chekh.paysage.feature.main.presentation.pager

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.addOnPageChangedListener
import com.chekh.paysage.core.extension.setParams
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.pager.PagerFragmentStateAdapter
import com.chekh.paysage.core.ui.pager.setBouncing
import com.chekh.paysage.core.ui.pager.transformer.ZoomOutPageTransformer
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.desktop.DesktopFragment
import com.chekh.paysage.feature.main.presentation.desktop.DesktopViewModel
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopGridProvider
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager.Companion.WIDGET_ATTACH_REQUEST_CODE
import com.chekh.paysage.feature.main.presentation.desktop.tools.getWidgetBounds
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_desktop_pager.*
import javax.inject.Inject

@AndroidEntryPoint
class DesktopPagerFragment :
    BaseFragment(R.layout.fragment_desktop_pager),
    DragAndDropListener {

    private val desktopViewModel: DesktopViewModel by activityViewModels()
    private val pagerViewModel: DesktopPagerViewModel by viewModels()

    @Inject
    lateinit var widgetHostManager: DesktopWidgetHostManager

    private val adapter by lazy { PagerFragmentStateAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupListeners()
        setupPager()
    }

    private fun setupListeners() {
        val activity = activity as? DesktopActivity
        activity?.addDragAndDropListener(this)
        vpDesktops.addOnPageChangedListener { position ->
            val pageId = adapter.getItemId(position)
            desktopViewModel.onPageChanged(pageId)
        }
    }

    @SuppressLint("WrongConstant")
    private fun setupPager() {
        vpDesktops.offscreenPageLimit = OFFSET_SCREEN_PAGE_LIMIT
        vpDesktops.setPageTransformer(ZoomOutPageTransformer())
        vpDesktops.setBouncing(true)
        vpDesktops.adapter = adapter
    }

    private fun setupViewModel() {
        pagerViewModel.init(Unit)
        pagerViewModel.pagesLiveData.observe(viewLifecycleOwner, ::setPages)
        pagerViewModel.desktopWidgetsUpdatesLiveData.observe(viewLifecycleOwner) {
            pagerViewModel.removeEmptyDesktopPages()
        }
        pagerViewModel.touchPageLiveData.observe(viewLifecycleOwner) { page ->
            if (page >= 0 && page < adapter.itemCount) {
                vpDesktops.currentItem = page
            } else {
                pagerViewModel.stopHandleDragTouch()
            }
        }
    }

    private fun setPages(pages: List<DesktopPageModel>) {
        if (pages.isEmpty()) {
            pagerViewModel.addLastDesktopPage()
            return
        }
        pages.sortedBy { it.position }
            .map {
                val fragment = adapter.getFragment(it.id) ?: DesktopFragment().apply {
                    setParams(DesktopFragment.Params(it.id))
                }
                it.id to fragment
            }
            .let { adapter.setFragments(it) }
    }

    override fun onDragStart(location: RectF, data: ClipData?) {
        pagerViewModel.addLastDesktopPage()
        val pageId = adapter.getItemId(vpDesktops.currentItem)
        afterPageStart(pageId) {
            onDraggingShadow(pageId, location, data)
        }
    }

    override fun onDragMove(touch: PointF, location: RectF, data: ClipData?) {
        val pageId = adapter.getItemId(vpDesktops.currentItem)
        val gridProvider = adapter.getFragment(pageId) as? DesktopGridProvider ?: return
        afterPageStart(pageId) {
            pagerViewModel.handleDragTouch(
                touch,
                vpDesktops.currentItem,
                gridProvider.getGridBounds()
            )
            onDraggingShadow(pageId, location, data)
        }
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        pagerViewModel.stopHandleDragTouch()
        val pageId = adapter.getItemId(vpDesktops.currentItem)
        afterPageStart(pageId) {
            when (data) {
                is WidgetClipData -> {
                    addDesktopWidget(pageId, location, data.widget)
                }
            }
        }
    }

    private fun onDraggingShadow(pageId: Long, location: RectF, data: ClipData?) {
        val gridProvider = adapter.getFragment(pageId) as? DesktopGridProvider ?: return
        when (data) {
            is WidgetClipData -> {
                val widget = data.widget
                val bounds = widget.getWidgetBounds(location)
                val cells = gridProvider.getOccupiedCells(bounds)
                desktopViewModel.setDraggingDesktopWidget(pageId, null, widget, cells)
            }
        }
    }

    private fun addDesktopWidget(pageId: Long, location: RectF, widget: WidgetModel) {
        val gridProvider = adapter.getFragment(pageId) as? DesktopGridProvider ?: return
        val widgetId = widgetHostManager.allocateDesktopWidgetId()
        val hasWidgetAttachPermissions = widgetHostManager.requestWidgetAttachPermissionsIfNeed(
            this,
            widgetId,
            widget.packageName,
            widget.className
        )
        val bounds = widget.getWidgetBounds(location)
        val cells = gridProvider.getOccupiedCells(bounds)
        if (hasWidgetAttachPermissions) {
            desktopViewModel.addDesktopWidget(pageId, widgetId, widget, cells)
            return
        }
        registerActivityResultListener(WIDGET_ATTACH_REQUEST_CODE) { resultCode, _ ->
            when (resultCode) {
                RESULT_OK -> desktopViewModel.addDesktopWidget(pageId, widgetId, widget, cells)
                else -> desktopViewModel.clearDraggingDesktopWidget()
            }
        }
    }

    private fun afterPageStart(pageId: Long, action: () -> Unit) {
        adapter.getFragment(pageId)?.view?.post { action() }
    }

    private companion object {
        const val OFFSET_SCREEN_PAGE_LIMIT = 100
    }
}
