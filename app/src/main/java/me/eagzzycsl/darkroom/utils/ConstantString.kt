package me.eagzzycsl.darkroom.utils

import android.os.Build


object ConstantString {
    object StateName {
        const val init = "~"
        const val mayBeNone = "myBeNone"
        const val firstShowAppDetail = "firstShowAppDetail"
        const val confirmDisable = "confirmDisable"
        const val afterConfirm = "afterConfirm"
    }

    object WindowChange {
        const val init = "~"
        const val none = ""
        const val outOfException = "!"
    }

    object WidgetId {
        const val buttonConfirm = "android:id/button1"
        val buttonEnableDisable
            get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                "com.android.settings:id/htc_disable_button"
            } else {
                "com.android.settings:id/htc_uninstall_button"
            }
    }

    object SettingActivityName {
        const val appDetail = "com.android.settings.applications.InstalledAppDetailsTop"
        const val appNotification = "com.android.settings.Settings\$AppNotificationSettingsActivity"
    }

    object EasyFreezeWidgetId {
        val appNameInNotify
            get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                "com.android.settings:id/app_detail_title"
            } else {
                "com.android.settings:id/app_name"
            }
        const val appNameFrameInAppDetail = "com.android.settings:id/app_text"
        const val appNameInAppDetail = "com.android.settings:id/app_detail_title"
    }

    object LiteralText {
        const val pack_age = "package"
    }

    const val pkgName = "pkgName"
    const val ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT"
    const val easyFreezeExtraKeyAppName = "appName"

    object NotifyChannel {
        const val easyFreezeChannelName = "快速冻结"
        const val easyFreezeChannelId = "easyFreeze"
    }
}
