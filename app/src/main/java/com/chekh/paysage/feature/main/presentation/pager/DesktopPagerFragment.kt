package com.chekh.paysage.feature.main.presentation.pager

import android.graphics.RectF
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.pager.PagerFragmentStateAdapter
import com.chekh.paysage.core.ui.pager.setBouncing
import com.chekh.paysage.core.ui.pager.transformer.ZoomOutPageTransformer
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import com.chekh.paysage.feature.main.presentation.MainActivity
import com.chekh.paysage.feature.main.presentation.desktop.DesktopFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_desktop_pager.*

@AndroidEntryPoint
class DesktopPagerFragment :
    BaseFragment(R.layout.fragment_desktop_pager),
    DragAndDropListener {

    private val pagerViewModel: DesktopPagerViewModel by viewModels()

    private val adapter by lazy { PagerFragmentStateAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupPager()
        setupViewModel()
    }

    private fun setupListeners() {
        val activity = activity as? MainActivity
        activity?.addDragAndDropListener(this)
    }

    private fun setupPager() {
        vpDesktops.setPageTransformer(ZoomOutPageTransformer())
        vpDesktops.setBouncing(true)
        vpDesktops.adapter = adapter
    }

    private fun setupViewModel() {
        pagerViewModel.init(Unit)
        pagerViewModel.pagesLiveData.observe(viewLifecycleOwner, ::setPages)
    }

    private fun setPages(pages: List<DesktopPageModel>) {
        if (pages.isEmpty()) {
            pagerViewModel.addDesktopPage(0)
            return
        }
        pages.sortedBy { it.position }
            .map {
                it.id to DesktopFragment().apply {
                    setParams(DesktopFragment.Params(it.id))
                }
            }
            .let { adapter.setFragments(it) }
    }

    override fun onDragStart(location: RectF, data: ClipData?) {
        onDragMove(location, data)
    }

    override fun onDragMove(location: RectF, data: ClipData?) {
        // TODO
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        // TODO
    }
}
