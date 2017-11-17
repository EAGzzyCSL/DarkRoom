package me.eagzzycsl.darkroom.state

import me.eagzzycsl.darkroom.utils.ConstantString

enum class WindowChange(private val changeName: String) {
    Init(ConstantString.WindowChange.init),
    AppDetail(ConstantString.setting_detail_activity_name),
    None(ConstantString.WindowChange.none),
    OutOfException(ConstantString.WindowChange.outOfException);

    private fun match(name: String): Boolean = this.changeName == name

    companion object {
        fun getChange(name: String): WindowChange {
            return WindowChange.values().firstOrNull { it.match(name) } ?: OutOfException
        }
    }

}