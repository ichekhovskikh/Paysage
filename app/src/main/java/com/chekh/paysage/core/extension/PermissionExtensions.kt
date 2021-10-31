package com.chekh.paysage.core.extension

import android.content.Context
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Fragment.checkPermissions(
    vararg permissions: String,
    onAccess: () -> Unit = {},
    onDenied: () -> Unit = {},
    onRationaleShouldBeShown: (permission: List<PermissionRequest>, token: PermissionToken) -> Unit = { _, _ -> },
) = context?.checkPermissions(
    *permissions,
    onAccess = onAccess,
    onDenied = onDenied,
    onRationaleShouldBeShown = onRationaleShouldBeShown
)

fun Context.checkPermissions(
    vararg permissions: String,
    onAccess: () -> Unit = {},
    onDenied: () -> Unit = {},
    onRationaleShouldBeShown: (permission: List<PermissionRequest>, token: PermissionToken) -> Unit = { _, _ -> },
) {
    Dexter.withContext(this)
        .withPermissions(permissions.toList())
        .withListener(
            object : MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        onAccess()
                    } else {
                        onDenied()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    onRationaleShouldBeShown(permission, token)
                }
            }
        )
        .check()
}
