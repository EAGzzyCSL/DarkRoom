package me.eagzzycsl.darkroom.model

import android.content.Context
import me.eagzzycsl.darkroom.manager.AppManager

import me.eagzzycsl.darkroom.manager.DarkManager

class NaughtyApp(metaApp: MetaApp) : MyApp(metaApp) {

    fun launch(context: Context) {
        if (isEnable(context)) {
            AppManager.launchApp(context, this)
        } else {
            DarkManager.release(this, context)
        }
    }

}
