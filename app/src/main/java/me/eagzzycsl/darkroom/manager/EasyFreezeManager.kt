package me.eagzzycsl.darkroom.manager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.view.accessibility.AccessibilityNodeInfo
import me.eagzzycsl.darkroom.EasyFreezeActivity
import me.eagzzycsl.darkroom.R
import me.eagzzycsl.darkroom.utils.AccessibilityUtils
import me.eagzzycsl.darkroom.utils.ConstantString
import android.app.NotificationChannel
import me.eagzzycsl.darkroom.MainActivity


object EasyFreezeManager {
    fun reactAppDetail(context: Context, nodeInfo: AccessibilityNodeInfo) {
        val appName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nodeFrame = AccessibilityUtils.findNodeById(
                    nodeInfo,
                    ConstantString.EasyFreezeWidgetId.appNameInAppDetail)
            nodeFrame?.text?.toString()
        } else {
            val nodeFrame = AccessibilityUtils.findNodeById(
                    nodeInfo,
                    ConstantString.EasyFreezeWidgetId.appNameFrameInAppDetail)
            nodeFrame?.getChild(0)?.text?.toString()
        }

        this.startEasyFreeze(context, appName)
    }

    fun reactAppNotifySettings(context: Context, nodeInfo: AccessibilityNodeInfo) {
        val appName = AccessibilityUtils.findNodeById(
                nodeInfo,
                ConstantString.EasyFreezeWidgetId.appNameInNotify)?.text as String
        this.startEasyFreeze(context, appName)
    }

    private fun startEasyFreeze(context: Context, appName: String?) {
        if (appName == null) {
            return
        }
        this.showEasyFreezeNotify(context, appName)
    }

    private fun showEasyFreezeNotify(context: Context, appName: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val easyFreezeIntent = Intent(context, EasyFreezeActivity::class.java)
        easyFreezeIntent.putExtra(ConstantString.easyFreezeExtraKeyAppName, appName)
        val pendingIntent = PendingIntent.getActivities(
                context, 0,
                arrayOf(
                        Intent(context, MainActivity::class.java),
                        easyFreezeIntent
                ), PendingIntent.FLAG_CANCEL_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(
                    ConstantString.NotifyChannel.easyFreezeChannelId,
                    ConstantString.NotifyChannel.easyFreezeChannelName,
                    NotificationManager.IMPORTANCE_DEFAULT))
        }
        val mBuilder = NotificationCompat.Builder(context, ConstantString.NotifyChannel.easyFreezeChannelId)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("加入冻结")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentText(appName)
                .setContentIntent(pendingIntent)
        notificationManager.notify(0, mBuilder.build())
    }
}