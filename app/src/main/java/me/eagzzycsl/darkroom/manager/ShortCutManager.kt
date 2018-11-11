package me.eagzzycsl.darkroom.manager

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.Toast
import me.eagzzycsl.darkroom.JumpActivity
import me.eagzzycsl.darkroom.model.MetaApp
import me.eagzzycsl.darkroom.utils.ConstantString

object ShortCutManager {
    fun createShortcut(context: Context, naughtyApp: MetaApp) {
        val addShortcutIntent = Intent(ConstantString.ACTION_ADD_SHORTCUT)
        addShortcutIntent.putExtra("duplicate", true)
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, naughtyApp.appName)
        // 快捷方式的图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, getIconBitmap(
                context,
                naughtyApp.appIcon
        ))
        val actionIntent = Intent(
                context,
                JumpActivity::class.java
        )
        actionIntent.putExtra(ConstantString.pkgName, naughtyApp.pkgName)
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent)
        context.sendBroadcast(addShortcutIntent)
        Toast.makeText(context, "快捷方式创建成功", Toast.LENGTH_SHORT).show()
    }

    private fun getIconBitmap(context: Context, drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

}