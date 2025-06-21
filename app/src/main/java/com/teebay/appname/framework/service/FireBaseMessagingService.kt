package com.teebay.appname.framework.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teebay.appname.core.domain.ProductUseCase
import com.teebay.appname.core.domain.TokenUseCase
import com.teebay.appname.core.domain.UserUseCase
import com.teebay.appname.framework.Notification.TeebayNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "FireBaseMessagingService"

@AndroidEntryPoint
class FireBaseMessagingService : FirebaseMessagingService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var tokenUseCase: TokenUseCase

    @Inject
    lateinit var userUseCase: UserUseCase

    @Inject
    lateinit var productUseCase: ProductUseCase

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle push message
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        val data = remoteMessage.data.toString()
        Log.d(TAG, "Message title: ${title}")
        Log.d(TAG, "Message body: ${body}")
        Log.d(TAG, "Message data: ${data}")

        if (!title.isNullOrBlank() && !body.isNullOrBlank() && data.isNotBlank()) {
            val transactionId = getTransactionIdFromNotificationData(data)
            if (transactionId != null) {
                Log.i(TAG,"Transaction ID: $transactionId")
                scope.launch {
                    userUseCase.getLoggedInUserCached()?.let { user ->
                        if (title.contains("purchased") && productUseCase.needToShowPurchasedNotification(
                                transactionId,
                                user.id
                            )
                        ) {
                            TeebayNotificationManager.showNotification(
                                this@FireBaseMessagingService,
                                title,
                                body
                            )

                        } else if (title.contains("rented") && productUseCase.needToShowRentedNotification(
                                transactionId,
                                user.id
                            )
                        ) {
                            TeebayNotificationManager.showNotification(
                                this@FireBaseMessagingService,
                                title,
                                body
                            )

                        }
                    }
                }
            } else {
                Log.i(TAG, "Transaction ID is null")
            }

        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New token: $token")
        tokenUseCase.saveToken(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getTransactionIdFromNotificationData(data: String): Int? {
        return data
            .removePrefix("{")
            .removeSuffix("}")
            .split("=")
            .getOrNull(1)
            ?.toIntOrNull()
    }

}