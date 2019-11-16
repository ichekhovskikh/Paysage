package com.chekh.paysage.ui.fragment

import android.os.Bundle
import android.view.View.OVER_SCROLL_NEVER
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.extension.applyPadding
import com.chekh.paysage.ui.fragment.core.ViewModelFragment
import com.chekh.paysage.ui.adapter.AppsCategoryAdapter
import com.chekh.paysage.extension.observe
import com.chekh.paysage.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_apps.*

class AppsFragment : ViewModelFragment<HomeViewModel>() {

    override val layoutId = R.layout.fragment_apps
    override val viewModelClass = HomeViewModel::class.java

    private var adapter: AppsCategoryAdapter? = null
    private var recyclerCreatedListener: ((RecyclerView) -> Unit)? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AppsCategoryAdapter()
        onAppsAdapterCreated()
    }

    private fun onAppsAdapterCreated() {
        initCategoryRecycler()
        viewModel.getAppsGroupByCategories(this) { categories ->
            adapter?.setAppsCategories(categories)
        }
    }

    private fun initCategoryRecycler() {
        categoryRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            overScrollMode = OVER_SCROLL_NEVER
        }
        categoryRecycler.adapter = adapter
        adapter?.setScrollToTopHeaderAction { categoryRecycler.scrollToTopHeader() }
        adapter?.setAnimationItemChangedAction { categoryRecycler.scheduleLayoutAnimation() }
        recyclerCreatedListener?.invoke(categoryRecycler)
    }

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        super.onViewModelCreated(savedInstanceState)
        val defaultRecyclerPaddingBottom = categoryRecycler.paddingBottom
        viewModel.navigationBarHeightLiveData.observe(this) { height ->
            categoryRecycler.applyPadding(bottom = defaultRecyclerPaddingBottom + height)
        }
    }

    fun setOnRecyclerCreatedListener(action: (RecyclerView) -> Unit) {
        recyclerCreatedListener = action
    }

    fun collapseAllCategories() {
        adapter?.collapseAll()
        categoryRecycler.scrollToPosition(0)
    }

    companion object {
        fun instance() = AppsFragment()
    }
}