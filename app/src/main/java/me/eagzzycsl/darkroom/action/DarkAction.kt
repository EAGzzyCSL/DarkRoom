package me.eagzzycsl.darkroom.action

import android.content.Context
import me.eagzzycsl.darkroom.state.MachineState
import me.eagzzycsl.darkroom.state.WindowChange
import me.eagzzycsl.darkroom.manager.AppManager
import me.eagzzycsl.darkroom.model.MetaApp
import me.eagzzycsl.darkroom.utils.ConstantString

abstract class DarkAction(app: MetaApp) : AutoAction(app) {
    private val maybeNoneState = MachineState(ConstantString.StateName.mayBeNone)
    val firstShowAppDetailState = MachineState(ConstantString.StateName.firstShowAppDetail)
    val confirmDisableState = MachineState(ConstantString.StateName.confirmDisable)
    val afterConfirmState = MachineState(ConstantString.StateName.afterConfirm)

    init {
        initState.setNext(WindowChange.None, maybeNoneState)
        initState.setNext(WindowChange.AppDetail, firstShowAppDetailState)
        maybeNoneState.setNext(WindowChange.AppDetail, firstShowAppDetailState)
        firstShowAppDetailState.setNext(WindowChange.None, confirmDisableState)
        confirmDisableState.setNext(WindowChange.AppDetail, afterConfirmState)
    }

    override fun onAccept(name: String) {
        val change = WindowChange.getChange(name)
        this.current = this.current?.accept(change)
    }

    override fun onStrike(context: Context) {
        AppManager.gotoSettings(context, app)
    }

}