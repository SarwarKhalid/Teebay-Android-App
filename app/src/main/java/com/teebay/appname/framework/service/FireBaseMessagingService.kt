package com.teebay.appname.framework.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teebay.appname.core.domain.TokenUseCase
import com.teebay.appname.framework.Notification.TeebayNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private val TAG = "FireBaseMessagingService"

@AndroidEntryPoint
class FireBaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var tokenUseCase: TokenUseCase

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle push message
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        val data = remoteMessage.data
        Log.d(TAG, "Message title: ${title}")
        Log.d(TAG, "Message body: ${body}")
        Log.d(TAG, "Message data: ${data}")

        if(!title.isNullOrBlank() && !body.isNullOrBlank()){
            TeebayNotificationManager.showNotification(this, title, body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New token: $token")
        tokenUseCase.saveToken(token)
    }
}