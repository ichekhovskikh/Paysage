package com.chekh.slidinguppanel

val SlidingUpPanelLayout.isClosed
    get() = panelState in listOf(
        SlidingUpPanelLayout.PanelState.HIDDEN,
        SlidingUpPanelLayout.PanelState.COLLAPSED
    )

val SlidingUpPanelLayout.isOpened
    get() = panelState == SlidingUpPanelLayout.PanelState.EXPANDED ||
            panelState == SlidingUpPanelLayout.PanelState.ANCHORED