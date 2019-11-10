package com.chekh.paysage.manager

import android.content.Context
import android.content.pm.LauncherActivityInfo
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.Process
import android.os.UserHandle

class AppManager(context: Context) {
    private val launcherApps = context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    val packageManager: PackageManager = context.packageManager

    fun getApps(packageName: String, userHandle: UserHandle = Process.myUserHandle()): List<LauncherActivityInfo> {
        return launcherApps.getActivityList(packageName, userHandle)
    }

    fun getAllApps(): List<LauncherActivityInfo> {
        return launcherApps.getActivityList(null, Process.myUserHandle())
    }

    fun addOnAppsChangedCallback(callback: LauncherApps.Callback) {
        launcherApps.registerCallback(callback)
    }

    fun removeOnAppsChangedCallback(callback: LauncherApps.Callback) {
        launcherApps.unregisterCallback(callback)
    }

    open class AppsChangedCallback : LauncherApps.Callback() {

        override fun onPackageRemoved(packageName: String, userHandle: UserHandle) {}

        override fun onPackageAdded(packageName: String, userHandle: UserHandle) {}

        override fun onPackageChanged(packageName: String, userHandle: UserHandle) {}

        override fun onPackagesAvailable(packageNames: Array<String>, userHandle: UserHandle, isAvailable: Boolean) {}

        override fun onPackagesUnavailable(packageNames: Array<String>, userHandle: UserHandle, isAvailable: Boolean) {}
    }

    abstract class AppsChangedCallbackWrapper : AppsChangedCallback() {

        override fun onPackageRemoved(packageName: String, userHandle: UserHandle) {
            onAppsChanged(packageName, userHandle)
        }

        override fun onPackageAdded(packageName: String, userHandle: UserHandle) {
            onAppsChanged(packageName, userHandle)
        }

        override fun onPackageChanged(packageName: String, userHandle: UserHandle) {
            onAppsChanged(packageName, userHandle)
        }

        abstract fun onAppsChanged(packageName: String, userHandle: UserHandle)
    }
}