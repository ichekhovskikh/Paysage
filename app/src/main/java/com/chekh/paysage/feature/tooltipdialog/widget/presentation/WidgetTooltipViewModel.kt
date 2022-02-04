package com.chekh.paysage.feature.tooltipdialog.widget.presentation

import com.chekh.paysage.core.extension.distinctUntilChanged
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.widget.domain.usecase.GetFirstAppForWidgetPackageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WidgetTooltipViewModel @Inject constructor(
    getFirstAppForWidgetPackageUseCase: GetFirstAppForWidgetPackageUseCase/*,
    args: WidgetTooltipFragment.Args*/  // todo SavedStateHandle
) : BaseViewModel<Unit>() {

    val widgetApp = getFirstAppForWidgetPackageUseCase("args.packageName").distinctUntilChanged()
}
