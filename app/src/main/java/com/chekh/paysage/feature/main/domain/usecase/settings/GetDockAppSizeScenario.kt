package com.chekh.paysage.feature.main.domain.usecase.settings

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.map
import javax.inject.Inject

class GetDockAppSizeScenario @Inject constructor(
    private val getDockAppSettingsUseCase: GetDockAppSettingsUseCase
) {

    operator fun invoke(): LiveData<Int> =
        getDockAppSettingsUseCase().map { it?.appSize }
}
