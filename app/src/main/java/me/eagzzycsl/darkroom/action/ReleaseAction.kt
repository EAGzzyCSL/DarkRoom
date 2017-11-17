package me.eagzzycsl.darkroom.action

import android.view.accessibility.AccessibilityNodeInfo
import me.eagzzycsl.darkroom.model.MyApp
import me.eagzzycsl.darkroom.utils.ConstantString

class ReleaseAction(app: MyApp) : DarkAction(app) {

    override fun onReact(nodeInfo: AccessibilityNodeInfo?) {
        when (current) {
            firstShowAppDetailState -> {
                this.performEnableClick(nodeInfo)
                this.resolve();
            }
        }
    }

    private fun performEnableClick(nodeInfo: AccessibilityNodeInfo?): Boolean {
        return this.findNodeById(
                nodeInfo,
                ConstantString.WidgetId.buttonEnableDisable


        )?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true;
    }

}