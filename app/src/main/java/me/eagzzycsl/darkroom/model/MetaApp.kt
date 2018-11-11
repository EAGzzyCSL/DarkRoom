package me.eagzzycsl.darkroom.model

import android.content.Context
import android.graphics.drawable.Drawable
import me.eagzzycsl.darkroom.manager.AppManager
import me.eagzzycsl.darkroom.manager.DarkManager


class MetaApp(
    val appName: String,
    val pkgName: String,
    val appIcon: Drawable,
    var isNaughty: Boolean =false,
    val isSysApp: Boolean = false
){
    var willFreeze = false;
    fun isEnable(context: Context): Boolean {
        return AppManager.isEnable(context, pkgName)
    }
    fun launch(context: Context) {
        if (isEnable(context)) {
            AppManager.launchApp(context, this)
        } else {
            DarkManager.release(this, context)
        }
    }
    fun toggleWillFreeze() {
        this.willFreeze = !willFreeze
    }
}