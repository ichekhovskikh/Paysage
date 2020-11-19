package com.chekh.paysage.feature.main.tools

import android.content.pm.LauncherApps
import android.os.UserHandle

abstract class AppsChangedCallback : LauncherApps.Callback() {

    override fun onPackageRemoved(packageName: String, userHandle: UserHandle) {
        onAppsChanged(packageName, userHandle)
    }

    override fun onPackageAdded(packageName: String, userHandle: UserHandle) {
        onAppsChanged(packageName, userHandle)
    }

    override fun onPackageChanged(packageName: String, userHandle: UserHandle) {
        onAppsChanged(packageName, userHandle)
    }

    override fun onPackagesAvailable(
        packageNames: Array<String>,
        userHandle: UserHandle,
        isAvailable: Boolean
    ) {
    }

    override fun onPackagesUnavailable(
        packageNames: Array<String>,
        userHandle: UserHandle,
        isAvailable: Boolean
    ) {
    }

    abstract fun onAppsChanged(packageName: String, userHandle: UserHandle)
}

fun onAppsChanged(action: (packageName: String, userHandle: UserHandle) -> Unit) =
    object : AppsChangedCallback() {
        override fun onAppsChanged(packageName: String, userHandle: UserHandle) {
            action(packageName, userHandle)
        }
    }
