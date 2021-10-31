package com.chekh.paysage.feature.main.presentation.desktop

import android.Manifest
import android.app.WallpaperManager
import android.graphics.Rect
import android.graphics.RectF
import android.os.*
import android.view.View
import androidx.core.graphics.toRectF
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.flow.FlowListAdapter
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.apps.AppDockViewModel
import com.chekh.paysage.feature.main.presentation.desktop.adapter.DesktopWidgetFlowListItem
import com.chekh.paysage.feature.main.presentation.desktop.tools.PageLocationProvider
import com.chekh.paysage.feature.main.presentation.options.DesktopOptionsFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_desktop.*
import javax.inject.Inject

@AndroidEntryPoint
class DesktopFragment : BaseFragment(R.layout.fragment_desktop), PageLocationProvider {

    private val desktopViewModel: DesktopViewModel by activityViewModels()
    private val appDockViewModel: AppDockViewModel by activityViewModels()

    @Inject
    lateinit var args: Args

    @Inject
    lateinit var wallpaperManager: WallpaperManager

    private val adapter by lazyUnsafe { FlowListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyWindowInsets()
        setupViews(view)
        setupListeners()
        setupViewModel()
    }

    private fun setupViews(view: View) {
        flWidgets.adapter = adapter

        checkPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            onAccess = {
                view.post { ravRipple.animationColor = wallpaperManager.fastColor }
            }
        )
    }

    private fun setupListeners() {
        flWidgets.setOnGestureLongPress { event ->
            event?.let { ravRipple.animateRipple(it.x, it.y) }
            activity?.supportFragmentManager?.commit {
                addWithBackStack(R.id.fcvContainer, DesktopOptionsFragment())
            }
        }
        wallpaperManager.onWallpaperChangedCompat {
            ravRipple.animationColor = wallpaperManager.fastColor
        }
        adapter.setOnItemsCommittedListener { items ->
            val dragItem = items.find { it is DesktopWidgetFlowListItem && it.isDragging }
            val bounds = dragItem?.let { flWidgets.getItemBounds(it) }
            bounds?.offset(flWidgets.startMargin, flWidgets.topMargin)
            (activity as? DesktopActivity)?.setTargetDragViewBounds(bounds?.toRectF())
        }
    }

    private fun setupViewModel() {
        desktopViewModel.desktopGridSize.observe(viewLifecycleOwner) { (columns, rows) ->
            flWidgets.setSize(columns.toInt(), rows.toInt())
        }
        desktopViewModel.getDesktopWidgets(args.pageId)
            .observe(viewLifecycleOwner) { items ->
                adapter.items = items
            }
        appDockViewModel.appDockSize.observe(viewLifecycleOwner) { appSize ->
            flWidgets.updatePadding(bottom = appSize)
        }
    }

    private fun applyWindowInsets() {
        flWidgets.applyInsetter {
            type(navigationBars = true, statusBars = true) {
                margin(animated = true)
            }
        }
    }

    override fun getOccupiedCells(bounds: RectF): Rect {
        flWidgets ?: return Rect()
        val offsetBounds = bounds.copyOffset(
            horizontalOffset = -flWidgets.startMargin.toFloat(),
            verticalOffset = -flWidgets.topMargin.toFloat()
        )
        return flWidgets.getOccupiedCells(offsetBounds)
    }

    override fun getPageBounds() = flWidgets?.bounds ?: Rect()

    @Parcelize
    data class Args(val pageId: Long) : Parcelable

    companion object {

        fun create(args: Args) = DesktopFragment().apply { setArgs(args) }
    }
}
