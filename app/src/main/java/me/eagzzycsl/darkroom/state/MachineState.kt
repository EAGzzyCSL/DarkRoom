package me.eagzzycsl.darkroom.state


import java.util.HashMap


class MachineState(val name: String) {
    private val next = HashMap<WindowChange, MachineState>()
    fun setNext(windowChange: WindowChange, machineState: MachineState) {
        this.next.put(windowChange, machineState)
    }

    fun accept(windowChange: WindowChange): MachineState? {
        return this.next[windowChange]
    }
}