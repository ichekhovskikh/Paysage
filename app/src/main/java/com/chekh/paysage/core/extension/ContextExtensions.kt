package com.chekh.paysage.core.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

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

fun Context.hasPermissions(vararg permissions: String) = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}
