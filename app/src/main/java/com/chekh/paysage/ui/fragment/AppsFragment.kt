package com.chekh.paysage.ui.fragment

import android.os.Bundle
import android.view.View.OVER_SCROLL_NEVER
import androidx.recyclerview.widget.LinearLayoutManager
import com.chekh.paysage.R
import com.chekh.paysage.ui.adapter.AppsCategoryAdapter
import com.chekh.paysage.ui.util.addNavigationBarMarginBottom
import com.chekh.paysage.ui.util.addNavigationBarPaddingBottom
import kotlinx.android.synthetic.main.fragment_apps.*

class AppsFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_apps

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initCategoryRecycler()
    }

    private fun initCategoryRecycler() {
        categoryRecycler.layoutManager = LinearLayoutManager(context)
        categoryRecycler.overScrollMode = OVER_SCROLL_NEVER
        categoryRecycler.adapter = AppsCategoryAdapter(listOf("", "", "", "", "", "", "", "", ""))
        addNavigationBarPaddingBottom(categoryRecycler)
    }

    companion object {
        fun instance() = AppsFragment()
    }
}