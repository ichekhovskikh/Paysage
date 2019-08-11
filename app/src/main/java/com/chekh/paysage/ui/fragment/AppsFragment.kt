package com.chekh.paysage.ui.fragment

import android.os.Bundle
import android.view.View.OVER_SCROLL_NEVER
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.model.AppsGroupByCategory
import com.chekh.paysage.ui.adapter.AppsCategoryAdapter
import com.chekh.paysage.ui.fragment.core.ViewModelFragment
import com.chekh.paysage.ui.util.applyPadding
import com.chekh.paysage.util.observe
import com.chekh.paysage.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_apps.*

class AppsFragment : ViewModelFragment<HomeViewModel>() {

    override val layoutId = R.layout.fragment_apps
    override val viewModelClass = HomeViewModel::class.java

    private lateinit var adapter: AppsCategoryAdapter
    private var recyclerCreatedListener: ((RecyclerView) -> Unit)? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AppsCategoryAdapter()
        onAppsAdapterCreated()
    }

    private fun onAppsAdapterCreated() {
        initCategoryRecycler()
        viewModel.getAppsGroupByCategories(this) { categories: List<AppsGroupByCategory> ->
            adapter.setAppsCategories(categories)
        }
    }

    private fun initCategoryRecycler() {
        categoryRecycler.adapter = adapter
        categoryRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            overScrollMode = OVER_SCROLL_NEVER
            recyclerCreatedListener?.invoke(this)
        }
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

    companion object {
        fun instance() = AppsFragment()
    }
}