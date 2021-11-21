package com.chekh.paysage.core.ui.contracts

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class StartActivityForResultLauncher(fragment: Fragment) {

    private var onResult: ((ActivityResult) -> Unit)? = null

    private val launcher: ActivityResultLauncher<Intent> = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        onResult?.invoke(activityResult)
    }

    operator fun invoke(
        intent: Intent,
        onResult: ((ActivityResult) -> Unit)? = null
    ) {
        this.onResult = onResult
        launcher.launch(intent)
    }
}
