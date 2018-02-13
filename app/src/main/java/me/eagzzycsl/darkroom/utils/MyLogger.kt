package me.eagzzycsl.darkroom.utils

import android.util.Log

enum class MyLogger(name: String) {

    Accessibility("无障碍服务"),
    Action("模拟操作"),
    Exception("异常"),
    Network("网络"),
    Notification("通知"),
    Freeze("冻结任务");

    fun i(s: String) {
        Log.i(this.name, s)
    }

    fun e(s: String) {
        Log.e(this.name, s);
    }

    fun i(tag: String, content: String) {
        Log.i(this.name, tag + "@" + content);
    }

    fun e(tag: String, content: String) {
        Log.i(this.name, tag + "@" + content);
    }
}
