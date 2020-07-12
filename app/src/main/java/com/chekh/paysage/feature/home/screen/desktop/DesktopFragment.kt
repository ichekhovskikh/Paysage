package com.chekh.paysage.feature.home.screen.desktop

import com.chekh.paysage.R
import com.chekh.paysage.ui.fragment.ViewModelFragment

class DesktopFragment : ViewModelFragment<DesktopViewModel>() {

    override val layoutId = R.layout.fragment_desktop
    override val viewModelClass = DesktopViewModel::class
}