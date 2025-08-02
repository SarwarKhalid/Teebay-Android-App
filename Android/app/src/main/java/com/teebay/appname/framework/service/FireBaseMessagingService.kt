package com.teebay.appname.framework.service

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teebay.appname.MainActivity
import com.teebay.appname.core.domain.ProductUseCase
import com.teebay.appname.core.domain.TokenUseCase
import com.teebay.appname.core.domain.UserUseCase
import com.teebay.appname.core.model.Result
import com.teebay.appname.framework.Notification.TeebayNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

        //TODO: Consider refactoring the logic to make it cleaner
        if (!title.isNullOrBlank() && !body.isNullOrBlank() && data.isNotBlank()) {
            val transactionId = getTransactionIdFromNotificationData(data)
            if (transactionId != null) {
                Log.i(TAG, "Transaction ID: $transactionId")
                scope.launch {
                    userUseCase.getLoggedInUserCached()?.let { user ->
                        if (title.contains("purchased") && productUseCase.needToShowPurchasedNotification(
                                transactionId,
                                user.id
                            )
                        ) {
                            showPurchasedProductNotification(title, body, transactionId)
                        } else if (title.contains("rented") && productUseCase.needToShowRentedNotification(
                                transactionId,
                                user.id
                            )
                        ) {
                            showRentedProductNotification(title, body, transactionId)
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

    private fun getPendingIntent(notificationProductId: Int): PendingIntent? {
        Log.d(TAG,"pending intent notificationProductId: $notificationProductId")
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("product_id", notificationProductId)
        }
        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private suspend fun showPurchasedProductNotification(title:String, body:String, transactionId: Int) {
        val purchasedProduct = productUseCase.getPurchasedProduct(transactionId)
        if (purchasedProduct is Result.Success) {
            TeebayNotificationManager.showNotification(
                this@FireBaseMessagingService,
                title,
                body,
                getPendingIntent(purchasedProduct.data.product)
            )
        }
    }

    private suspend fun showRentedProductNotification(title:String, body:String, transactionId: Int) {
        val rentedProduct = productUseCase.getRentedProduct(transactionId)
        if (rentedProduct is Result.Success) {
            TeebayNotificationManager.showNotification(
                this@FireBaseMessagingService,
                title,
                body,
                getPendingIntent(rentedProduct.data.product)
            )
        }
    }

}