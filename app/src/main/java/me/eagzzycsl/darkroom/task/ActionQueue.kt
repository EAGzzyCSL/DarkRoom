package me.eagzzycsl.darkroom.task

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import me.eagzzycsl.darkroom.action.AutoAction
import java.util.*


object ActionQueue {
    val allResolve: Boolean
        get() = doneAmount == actions.size;
    private var doneAmount = 0;
    private var cursor = 0;
    val actions: LinkedList<AutoAction> = LinkedList<AutoAction>()
    fun reset() {
        actions.clear()
        doneAmount = 0;
        cursor = 0;
    }

    @Synchronized
    fun running(context: Context): Int {
        if (cursor < actions.size) {
            val inAction = actions[cursor]
            when (inAction.status) {
                AutoAction.ActionStatus.Pending -> {
                    inAction.strike(context)
                }
                AutoAction.ActionStatus.Done -> {
                    // 完成度+1
                    cursor++;
                    doneAmount++;
                }
                AutoAction.ActionStatus.Running -> {
                    // 处于running状态的action会由AutoService调用
                }
            }
        }
        return cursor
    }

    @Synchronized
    fun append(action: AutoAction) {
        actions.offer(action)
    }

    @Synchronized
    fun appendAll(newActions: List<AutoAction>) {
        actions.addAll(newActions)
    }

    fun accept(name: String) {
        if (cursor < actions.size) {
            actions[cursor].accept(name)
        }
    }

    fun react(nodeInfo: AccessibilityNodeInfo) {
        if (cursor < actions.size) {
            actions[cursor].react(nodeInfo)
        }
    }

}
