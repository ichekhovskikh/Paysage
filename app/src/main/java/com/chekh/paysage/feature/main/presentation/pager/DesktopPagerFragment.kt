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
import com.chekh.paysage.core.tools.lazyUnsafe
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
import com.chekh.paysage.feature.main.presentation.desktop.tools.PageLocationProvider
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import com.chekh.paysage.feature.main.presentation.desktop.tools.getWidgetBounds
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

    private val adapter by lazyUnsafe { PagerFragmentStateAdapter(this) }

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
        desktopViewModel.init(Unit)
        pagerViewModel.pages.observe(viewLifecycleOwner, ::setPages)
        pagerViewModel.touchPage.observe(viewLifecycleOwner) { page ->
            if (page >= 0 && page < adapter.itemCount) {
                vpDesktops.currentItem = page
            } else {
                pagerViewModel.stopHandlePageDragTouch()
            }
        }
        desktopViewModel.onFailApplyChanges.observe(viewLifecycleOwner) {
            pagerViewModel.removeLastDesktopPage()
        }
    }

    private fun setPages(pages: List<DesktopPageModel>) {
        if (pages.isEmpty()) {
            pagerViewModel.addLastDesktopPage()
            return
        }
        pages.sortedBy { it.position }
            .map {
                val fragment = adapter.getFragment(it.id)
                    ?: DesktopFragment.create(DesktopFragment.Args(it.id))
                it.id to fragment
            }
            .let { adapter.setFragments(it) }
    }

    override fun onDragStart(location: RectF, data: ClipData?) {
        pagerViewModel.addLastDesktopPage()
        onDragMove(null, location, data)
    }

    override fun onDragMove(touch: PointF?, location: RectF, data: ClipData?) {
        val pageId = adapter.getItemId(vpDesktops.currentItem)
        val pageLocationProvider = adapter.getFragment(pageId) as? PageLocationProvider ?: return
        afterPageStart(pageId) {
            pagerViewModel.handlePageDragTouch(
                touch,
                vpDesktops.currentItem,
                pageLocationProvider.getPageBounds()
            )
            when (data) {
                is WidgetClipData -> {
                    val bounds = getWidgetBounds(location, data.width, data.height)
                    val cells = pageLocationProvider.getOccupiedCells(bounds)
                    desktopViewModel.onDragMove(pageId, data, cells)
                }
            }
        }
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        pagerViewModel.stopHandlePageDragTouch()
        val pageId = adapter.getItemId(vpDesktops.currentItem)
        afterPageStart(pageId) {
            when (data) {
                is WidgetClipData -> {
                    if (data.id.isNullOrBlank()) {
                        requestWidgetAttachPermissions(
                            packageName = data.packageName,
                            className = data.className,
                            onDenied = { desktopViewModel.onDragCancel() },
                            onAccess = { widgetId ->
                                desktopViewModel.attachIdentifierToDragWidget(widgetId)
                                desktopViewModel.onApplyDragChanges()
                            }
                        )
                    } else {
                        desktopViewModel.onApplyDragChanges()
                    }
                }
            }
        }
    }

    private fun requestWidgetAttachPermissions(
        packageName: String,
        className: String,
        onAccess: (widgetId: Int) -> Unit,
        onDenied: () -> Unit
    ) {
        val widgetId = widgetHostManager.allocateDesktopWidgetId()
        val attachPermissionsIntent = widgetHostManager
            .getWidgetAttachPermissionsIntent(widgetId, packageName, className)

        if (attachPermissionsIntent == null) {
            onAccess(widgetId)
            return
        }
        startActivityForResult(attachPermissionsIntent) { resultCode, _ ->
            when (resultCode) {
                RESULT_OK -> onAccess(widgetId)
                else -> onDenied()
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
