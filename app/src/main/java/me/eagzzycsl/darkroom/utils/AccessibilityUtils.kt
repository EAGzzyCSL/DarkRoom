package me.eagzzycsl.darkroom.utils

import android.view.accessibility.AccessibilityNodeInfo

object AccessibilityUtils {
    fun findNodeById(nodeInfo: AccessibilityNodeInfo?, id: String): AccessibilityNodeInfo? {
        val nodes = nodeInfo?.findAccessibilityNodeInfosByViewId(id)
        return if(nodes!=null ){
            if(nodes.size> 0 ){
                nodes[0]
            } else
                null
        }else {
            null
        }
    }
}