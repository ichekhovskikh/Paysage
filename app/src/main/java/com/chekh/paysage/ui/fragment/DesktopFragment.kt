package com.chekh.paysage.ui.fragment

import com.chekh.paysage.R
import com.chekh.paysage.ui.fragment.core.ViewModelFragment
import com.chekh.paysage.viewmodel.HomeViewModel

class DesktopFragment : ViewModelFragment<HomeViewModel>() {

    override val layoutId = R.layout.fragment_desktop
    override val viewModelClass = HomeViewModel::class.java

    companion object {
        fun instance() = DesktopFragment()
    }
}