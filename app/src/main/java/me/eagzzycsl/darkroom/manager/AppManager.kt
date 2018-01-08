package me.eagzzycsl.darkroom.manager

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import me.eagzzycsl.darkroom.MainActivity
import me.eagzzycsl.darkroom.R
import me.eagzzycsl.darkroom.model.MyApp
import me.eagzzycsl.darkroom.utils.ConstantString
import me.eagzzycsl.darkroom.utils.MyConfig

object AppManager {
    private val enableState = arrayOf(
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    )

    fun launchApp(context: Context, app: MyApp) {
        // 使用循环保证intent不为null
        var times = 0
        do {
            val intent = context.packageManager.getLaunchIntentForPackage(app.pkgName)
            times++
            if (intent != null) {
                context.startActivity(intent)
                return
            }
        } while (times < MyConfig.retryTimes)
        Toast.makeText(context, R.string.launchFailedPleaseManual, Toast.LENGTH_SHORT).show()
    }

    fun isEnable(context: Context, app: MyApp): Boolean {
        return enableState.contains(context.
                packageManager.
                getApplicationEnabledSetting(app.pkgName)
        )
    }

    fun gotoSettings(context: Context, myApp: MyApp) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(ConstantString._package, myApp.pkgName, null)
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