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

    val _package = "package"
    val setting_detail_activity_name = "com.android.settings.applications.InstalledAppDetailsTop"
    val pkgName = "pkgName"
    val ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT"
}
