package com.chekh.paysage.feature.tooltipdialog.widget.presentation

import androidx.hilt.lifecycle.ViewModelInject
import com.chekh.paysage.core.extension.distinctUntilChanged
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.widget.domain.usecase.GetFirstAppForWidgetPackageUseCase

class WidgetTooltipViewModel @ViewModelInject constructor(
    getFirstAppForWidgetPackageUseCase: GetFirstAppForWidgetPackageUseCase/*,
    args: WidgetTooltipFragment.Args*/  // todo SavedStateHandle
) : BaseViewModel<Unit>() {

    val widgetApp = getFirstAppForWidgetPackageUseCase("args.packageName").distinctUntilChanged()
}
