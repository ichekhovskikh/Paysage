package com.chekh.paysage.ui.fragment

import com.chekh.paysage.R

class DesktopFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_desktop

    companion object {
        fun instance() = DesktopFragment()
    }
}