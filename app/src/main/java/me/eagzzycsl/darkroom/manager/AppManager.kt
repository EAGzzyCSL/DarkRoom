package me.eagzzycsl.darkroom.manager

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import me.eagzzycsl.darkroom.MainActivity
import me.eagzzycsl.darkroom.R
import me.eagzzycsl.darkroom.model.MetaApp
import me.eagzzycsl.darkroom.utils.ConstantString
import me.eagzzycsl.darkroom.utils.MyConfig

object AppManager {
    private val enableState = arrayOf(
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    )

    fun launchApp(context: Context, app: MetaApp) {
        // 使用循环保证intent不为null
        var times = 0
        do {
            println("pkgname: ${app.pkgName}")
            val intent = context.packageManager.getLaunchIntentForPackage(app.pkgName)
            times++
            if (intent != null) {
                context.startActivity(intent)
                return
            }
        } while (times < MyConfig.retryTimes)
        Toast.makeText(context, R.string.launchFailedPleaseManual, Toast.LENGTH_SHORT).show()
    }

    fun isEnable(context: Context, pkgName: String): Boolean {
        return enableState.contains(
                context.packageManager.getApplicationEnabledSetting(pkgName)
        )
    }

    fun gotoSettings(context: Context, myApp: MetaApp) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(ConstantString.LiteralText.pack_age, myApp.pkgName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    fun letMeTop(context: Context) {
        val intent = Intent(
                context, MainActivity::class.java
        )
        context.startActivity(intent)
    }
}