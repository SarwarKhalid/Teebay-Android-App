package com.teebay.appname.framework.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.teebay.appname.R

private val TAG = "TeebayNotificationManager"

object TeebayNotificationManager {

//    private const val CHANNEL_ID = "Teebay_Notification_Channel"

    private var notificationChannelCreated = false
    private const val CHANNEL_ID = "default_channel_id"
    private const val NOTIFICATION_ID = 1

    fun showNotification(context: Context, title: String, message: String) {
        Log.i(TAG, "showNotification")
        val notificationManager = getSystemService(context, NotificationManager::class.java)
        createNotificationChannel(context, notificationManager)
        notificationManager?.let { notificationManager ->
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .build()
            Log.i(TAG, "showing notification")
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    fun isNotificationPermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Permission automatically granted for < API 33
        }
    }

    private fun createNotificationChannel(context: Context, notificationManager: NotificationManager?) {
        if (notificationChannelCreated) return
        //Create channel (required for Android 8+)
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "General notifications"
        }
        notificationManager?.createNotificationChannel(channel)
        notificationChannelCreated = true
    }
}