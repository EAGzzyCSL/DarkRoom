package me.eagzzycsl.darkroom.utils


object ConstantString {
    object StateName {
        val init = "~"
        val mayBeNone = "myBeNone"
        val firstShowAppDetail = "firstShowAppDetail"
        val confirmDisable = "confirmDisable"
        val afterConfirm = "afterConfirm"
    }

    object WindowChange {
        val init = "~"
        val none = ""
        val outOfException = "!"
    }

    object WidgetId {
        val buttonConfirm = "android:id/button1"
        val buttonEnableDisable = "com.android.settings:id/htc_uninstall_button"
    }

    object SettingActivityName {
        val appDetail = "com.android.settings.applications.InstalledAppDetailsTop"
        val appNotification ="com.android.settings.Settings\$AppNotificationSettingsActivity"
    }
    object EasyFreezeWidgetId {
        val appNameInNotify = "com.android.settings:id/app_name"
        val appNameFrameInAppDetail= "com.android.settings:id/app_text"
    }
    val _package = "package"
    val pkgName = "pkgName"
    val ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT"
    val easyFreezeExtraKeyAppName="appName"
}
