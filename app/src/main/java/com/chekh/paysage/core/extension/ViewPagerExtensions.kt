package com.chekh.paysage.core.extension

import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.addOnPageChangedListener(action: (Int) -> Unit) {
    registerOnPageChangeCallback(
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                action(position)
            }
        }
    )
}
