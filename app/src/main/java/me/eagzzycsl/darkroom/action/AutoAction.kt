package me.eagzzycsl.darkroom.action

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import me.eagzzycsl.darkroom.model.MetaApp
import me.eagzzycsl.darkroom.state.MachineState
import me.eagzzycsl.darkroom.utils.ConstantString

abstract class AutoAction(val app: MetaApp) {
    enum class ActionStatus {
        Pending,
        Running,
        Done,
    }

    var status = ActionStatus.Pending;
    val initState = MachineState(ConstantString.StateName.init);
    var current: MachineState? = initState;


    fun accept(name: String) {
        if (this.status != ActionStatus.Running) {
            return
        }
        this.onAccept(name);
    }

    abstract fun onAccept(name: String)

    fun react(nodeInfo: AccessibilityNodeInfo?) {
        onReact(nodeInfo);
    }

    @Synchronized
    fun strike(context: Context) {
        this.status = ActionStatus.Running
        this.onStrike(context)
    }

    abstract fun onReact(nodeInfo: AccessibilityNodeInfo?)


    abstract fun onStrike(context: Context)
    @Synchronized
    fun resolve() {
        this.status = ActionStatus.Done
    }


}