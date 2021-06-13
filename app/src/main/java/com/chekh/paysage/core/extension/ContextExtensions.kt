package com.chekh.paysage.core.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Context.startApp(packageName: String, className: String) {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        setClassName(packageName, className)
    }
    startActivityIfFound(intent)
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.startActivityIfFound(intent: Intent): Boolean {
    val packageManager = packageManager ?: return false
    val activityInfo = intent.resolveActivityInfo(packageManager, intent.flags) ?: return false
    if (activityInfo.exported) {
        startActivity(intent)
        return true
    }
    return false
}

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
