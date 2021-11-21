package com.chekh.paysage.core.ui.permissions

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.chekh.paysage.core.extension.hasPermissions
import java.lang.ref.WeakReference

class PermissionsLauncher(fragment: Fragment) {

    private val contextRef = WeakReference(fragment.context)

    private var onAccessGranted: (() -> Unit)? = null
    private var onAccessDenied: (() -> Unit)? = null

    private val activityResultLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        when (results.all { it.value }) {
            true -> onAccessGranted?.invoke()
            else -> onAccessDenied?.invoke()
        }
        onAccessGranted = null
        onAccessDenied = null
    }

    operator fun invoke(
        permissions: Array<out String>,
        onAccessGranted: (() -> Unit)? = null,
        onAccessDenied: (() -> Unit)? = null
    ) {
        if (contextRef.get()?.hasPermissions(*permissions) == true) {
            onAccessGranted?.invoke()
            return
        }
        this.onAccessGranted = onAccessGranted
        this.onAccessDenied = onAccessDenied
        activityResultLauncher.launch(permissions)
    }
}
