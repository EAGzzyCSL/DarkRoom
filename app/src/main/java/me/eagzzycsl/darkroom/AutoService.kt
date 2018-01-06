package me.eagzzycsl.darkroom

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.pm.PackageManager.NameNotFoundException
import android.view.accessibility.AccessibilityEvent
import me.eagzzycsl.darkroom.manager.DarkManager
import me.eagzzycsl.darkroom.manager.EasyFreezeManager

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
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val activityName = getActivityName(event)
            if (rootInActiveWindow == null) {

            } else {

                if (DarkManager.inWorking) {
                    ActionQueue.accept(activityName)
                    ActionQueue.react(rootInActiveWindow)
                } else {
                    if (activityName == ConstantString.SettingActivityName.appNotification) {
                        EasyFreezeManager.reactAppNotifySettings(this, rootInActiveWindow)
                    }
                    if (activityName == ConstantString.SettingActivityName.appDetail) {
                        EasyFreezeManager.reactAppDetail(this, rootInActiveWindow)
                    }
                }
            }
        }

    }

    override fun onInterrupt() {

    }
}
