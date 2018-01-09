package me.eagzzycsl.darkroom.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE


object MyConfig {
    val retryTimes = 100
    val DEBUG = false
    val NO_CHECK_ACCESS = false

    private object ConfigSP {
        val spName = ""
        val key_showGuideActivity = "showGuideActivity"
    }

    fun ifShowGuideActivity(context: Context): Boolean {
        val sp = context.getSharedPreferences(ConfigSP.spName, MODE_PRIVATE)
        return sp.getBoolean(ConfigSP.key_showGuideActivity, true)
    }

    fun doNotShowGuideActivity(context: Context) {
        val sp = context.getSharedPreferences(ConfigSP.spName, MODE_PRIVATE)
        sp.edit().putBoolean(ConfigSP.key_showGuideActivity, false).apply()
    }
}
