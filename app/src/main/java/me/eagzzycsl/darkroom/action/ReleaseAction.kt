package me.eagzzycsl.darkroom.action

import android.view.accessibility.AccessibilityNodeInfo
import me.eagzzycsl.darkroom.model.MetaApp
import me.eagzzycsl.darkroom.utils.AccessibilityUtils
import me.eagzzycsl.darkroom.utils.ConstantString

class ReleaseAction(app: MetaApp) : DarkAction(app) {

    override fun onReact(nodeInfo: AccessibilityNodeInfo?) {
        when (current) {
            firstShowAppDetailState -> {
                this.performEnableClick(nodeInfo)
                this.resolve();
            }
        }
    }

    private fun performEnableClick(nodeInfo: AccessibilityNodeInfo?): Boolean {
        return AccessibilityUtils.findNodeById(
                nodeInfo,
                ConstantString.WidgetId.buttonEnableDisable
        )?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true;
    }

}