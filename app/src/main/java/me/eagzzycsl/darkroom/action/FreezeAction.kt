package me.eagzzycsl.darkroom.action

import android.view.accessibility.AccessibilityNodeInfo
import me.eagzzycsl.darkroom.model.MyApp
import me.eagzzycsl.darkroom.utils.ConstantString

class FreezeAction(app: MyApp) : DarkAction(app) {

    override fun onReact(nodeInfo: AccessibilityNodeInfo?) {
        when (current) {
            firstShowAppDetailState -> {
                // disable
                if (this.performDisableClick(nodeInfo)) else this.resolve()
            }
            confirmDisableState -> {
                // confirm
                if (this.performConfirmClick(nodeInfo)) else this.resolve()
            }
            afterConfirmState -> {
                this.resolve()
            }
        }
    }


    private fun performConfirmClick(nodeInfo: AccessibilityNodeInfo?): Boolean {
        return this.findNodeById(
                nodeInfo,
                ConstantString.WidgetId.buttonConfirm
        )?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true;
    }

    private fun performDisableClick(nodeInfo: AccessibilityNodeInfo?): Boolean {
        return this.findNodeById(
                nodeInfo,
                ConstantString.WidgetId.buttonEnableDisable
        )?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true;
    }

}
