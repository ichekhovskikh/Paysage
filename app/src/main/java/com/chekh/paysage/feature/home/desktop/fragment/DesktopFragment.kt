package com.chekh.paysage.feature.home.desktop.fragment

import com.chekh.paysage.R
import com.chekh.paysage.ui.fragment.ViewModelFragment
import com.chekh.paysage.feature.home.HomeViewModel

class DesktopFragment : ViewModelFragment<HomeViewModel>() {

    override val layoutId = R.layout.fragment_desktop
    override val viewModelClass = HomeViewModel::class.java
}