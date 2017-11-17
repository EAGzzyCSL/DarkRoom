package me.eagzzycsl.darkroom

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.pm.PackageManager
import android.view.accessibility.AccessibilityEvent
import me.eagzzycsl.darkroom.manager.DarkManager

import me.eagzzycsl.darkroom.task.ActionQueue


class AutoService : AccessibilityService() {
    override fun onCreate() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (DarkManager.inWorking &&
                event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                ) {
            val activityComponentName = ComponentName(
                    event.packageName.toString(),
                    event.className.toString()
            )
            try {
                val packageManager = packageManager
                val activityInfo = packageManager.getActivityInfo(activityComponentName, 0)
                ActionQueue.accept(activityInfo.name)
            } catch (e: PackageManager.NameNotFoundException) {
                ActionQueue.accept("")
            }
            ActionQueue.react(rootInActiveWindow)
        }

    }

    override fun onInterrupt() {

    }
}
