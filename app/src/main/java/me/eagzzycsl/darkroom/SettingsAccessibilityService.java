package me.eagzzycsl.darkroom;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import java.util.List;

import me.eagzzycsl.darkroom.access.ActivityChange;
import me.eagzzycsl.darkroom.access.SettingsStateMachineManager;
import me.eagzzycsl.darkroom.uitls.ConstantString;
import me.eagzzycsl.darkroom.uitls.MyLogger;


public class SettingsAccessibilityService extends AccessibilityService {
    @Override
    public void onCreate() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int type = accessibilityEvent.getEventType();
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (accessibilityEvent.getPackageName() == null) {
                return;
            }
            ComponentName activityComponentName = new ComponentName(
                    accessibilityEvent.getPackageName().toString(),
                    accessibilityEvent.getClassName().toString()
            );
            try {
                PackageManager packageManager = getPackageManager();
                ActivityInfo activityInfo = packageManager.getActivityInfo(activityComponentName, 0);
                ActivityChange change = ActivityChange.getState(activityInfo.name);
                SettingsStateMachineManager.getInstance().accept(change);
                MyLogger.accessibility.i("窗体状态改变", activityInfo.name);
            } catch (PackageManager.NameNotFoundException e) {
                SettingsStateMachineManager.getInstance().accept(ActivityChange.None);
                MyLogger.accessibility.i("窗体状态改变", "None");
            }
            if (SettingsStateMachineManager.getInstance().isWorkingInDisable()) {
                if (SettingsStateMachineManager.getInstance().readyToDisable()) {
                    this.performDisableClick(accessibilityEvent);
                }
                if (SettingsStateMachineManager.getInstance().readyToConfirm()) {
                    this.performConfirmClick(accessibilityEvent);
                }
                if (SettingsStateMachineManager.getInstance().readyToNext()) {
                    MyLogger.accessibility.i("一个冻结完成");
                    performBack();
                    SettingsStateMachineManager.getInstance().resetDisable();
                }
            }
            if (SettingsStateMachineManager.getInstance().isWorkingInEnable()) {
                if (SettingsStateMachineManager.getInstance().readyToEnable()) {
                    this.performEnableClick(accessibilityEvent);
                    performBack();
                    SettingsStateMachineManager.getInstance().getCurrentWorkingApp().launch(getApplicationContext());
                }
            }

        }
    }

    private AccessibilityNodeInfo findNode(AccessibilityEvent accessibilityEvent, String widget, String text) {
        if (getRootInActiveWindow() == null) {
            return null;
        }

        List<AccessibilityNodeInfo> nodes = accessibilityEvent.getSource().findAccessibilityNodeInfosByText(text);
        if (nodes != null) {
            for (AccessibilityNodeInfo node : nodes) {
                if ((widget == null || node.getClassName().equals(widget)) && node.isEnabled()) {
                    return node;
                }
            }
        }
        return null;
    }

    private AccessibilityNodeInfo findNode(AccessibilityEvent accessibilityEvent, String widget, String[] text) {
        for (String s : text) {
            AccessibilityNodeInfo nodeInfo = findNode(accessibilityEvent, widget, s);
            if (nodeInfo != null) {
                return nodeInfo;
            }
        }
        return null;
    }

    private boolean performDisableClick(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo node = this.findNode(
                accessibilityEvent,
                Button.class.getName(),
                ConstantString.disable
        );
        return node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    private boolean performEnableClick(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo node = this.findNode(
                accessibilityEvent,
                Button.class.getName(),
                ConstantString.enable

        );
        return node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    private boolean performConfirmClick(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo node = this.findNode(
                accessibilityEvent,
                null,
                ConstantString.confirm

        );
        return node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    private boolean performBack() {
        return performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    @Override
    public void onInterrupt() {

    }
}
