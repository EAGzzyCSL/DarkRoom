package me.eagzzycsl.darkroom.manager

import android.content.Context
import me.eagzzycsl.darkroom.R

object SettingsManager {
    private val preName = "me.eagzzycsl.darkroom_preferences"
    var autoFinishAfterFreeze = false
    var autoFinishAfterLaunch = false
    var showEasyFreezeInDetail = false
    var showEasyFreezeInNotify = false

    fun updateValues(context: Context) {
        val sp = context.getSharedPreferences(preName, Context.MODE_PRIVATE)
        this.autoFinishAfterFreeze = sp.getBoolean(
                context.getString(R.string.key_autoFinishAfterFreeze), false)
        this.autoFinishAfterLaunch = sp.getBoolean(
                context.getString(R.string.key_autoFinishAfterLaunch), false)
        this.showEasyFreezeInDetail = sp.getBoolean(
                context.getString(R.string.key_showEasyFreezeInDetail), false)
        this.showEasyFreezeInNotify = sp.getBoolean(
                context.getString(R.string.key_showEasyFreezeInNotify), false)
    }
}
