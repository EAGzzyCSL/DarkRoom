package me.eagzzycsl.darkroom.model

import android.content.Context
import android.graphics.drawable.Drawable
import me.eagzzycsl.darkroom.manager.AppManager


abstract class MyApp(val metaApp: MetaApp) {

    val appName: String
        get() = metaApp.appName

    val pkgName: String
        get() = metaApp.pkgName

    val appIcon: Drawable
        get() = metaApp.appIcon

    fun isEnable(context: Context): Boolean {
        return AppManager.isEnable(context, this)
    }

}
