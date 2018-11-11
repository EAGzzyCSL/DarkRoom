package me.eagzzycsl.darkroom

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.pm.PackageManager.NameNotFoundException
import android.view.accessibility.AccessibilityEvent
import me.eagzzycsl.darkroom.manager.DarkManager
import me.eagzzycsl.darkroom.manager.EasyFreezeManager
import me.eagzzycsl.darkroom.manager.SettingsManager

import me.eagzzycsl.darkroom.task.ActionQueue
import me.eagzzycsl.darkroom.utils.ConstantString


class AutoService : AccessibilityService() {
    override fun onCreate() {
    }

    private fun getActivityName(event: AccessibilityEvent): String {
        val activityComponentName = ComponentName(
                event.packageName.toString(),
                event.className.toString()
        )
        try {
            val packageManager = packageManager
            val activityInfo = packageManager.getActivityInfo(activityComponentName, 0)
            return activityInfo.name
        } catch (e: NameNotFoundException) {
        }
        return ""
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        println("onAccessEvent")
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            println("isWindowEvent")
            val activityName = getActivityName(event)
            if (rootInActiveWindow == null) {
                println("rootIsNull")
            } else {
                println("rootNotNull")
                if (DarkManager.inWorking) {
                    println("inWorking");
                    ActionQueue.accept(activityName)
                    ActionQueue.react(rootInActiveWindow)
                } else {
                    if (SettingsManager.showEasyFreezeInNotify
                            && activityName == ConstantString.SettingActivityName.appNotification) {
                        EasyFreezeManager.reactAppNotifySettings(this, rootInActiveWindow)
                    }
                    if (SettingsManager.showEasyFreezeInDetail
                            && activityName == ConstantString.SettingActivityName.appDetail) {
                        EasyFreezeManager.reactAppDetail(this, rootInActiveWindow)
                    }
                }
            }
        }

    }

    override fun onInterrupt() {

    }
}
