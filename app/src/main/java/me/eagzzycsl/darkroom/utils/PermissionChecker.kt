package me.eagzzycsl.darkroom.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.support.v7.app.AlertDialog

object PermissionChecker {


    private fun isAccessibilityServiceEnabled(context: Context): Boolean {
        var accessibilityEnabled = 0
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.contentResolver,
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: Settings.SettingNotFoundException) {
            MyLogger.Exception.i("获取access状态失败")
        }

        if (accessibilityEnabled == 1) {
            val services = Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (services != null) {
                return services.toLowerCase().contains(context.packageName.toLowerCase())
            }
        }

        return false
    }


    private fun gotoAccessibilitySettings(context: Context) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        context.startActivity(intent)
    }

    fun requireAccessibility(context: Context): Boolean {
        if (!this.isAccessibilityServiceEnabled(context)) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("无障碍权限未授予，程序正常工作需要该权限，是否立即授予？")
                    .setNegativeButton("否") { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("是") { dialogInterface, _ ->
                        this.gotoAccessibilitySettings(context)
                        dialogInterface.dismiss()
                    }
                    .create().show()
            return false;
        }
        return true;
    }

}