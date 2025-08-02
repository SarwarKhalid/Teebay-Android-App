package com.teebay.appname.framework.database.datasourceimpl

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.teebay.appname.core.data.datasource.ITokenDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val TAG = "TokenDataSourceImpl"

private val PREFS = "prefs"
private val FCM_TOKEN = "fcm_token"

class TokenDataSourceImpl @Inject constructor(@ApplicationContext context: Context): ITokenDataSource {

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        Log.i(TAG,"saveToken")
        prefs.edit().putString(FCM_TOKEN, token).apply()
    }

    override suspend fun getToken(): String? {
        Log.i(TAG,"getToken")
        val token = prefs.getString(FCM_TOKEN, null) ?: getTokenFromFirebase()
        return token.also {
            Log.d(TAG,token.toString())
        }
    }

    override suspend fun refreshToken() {
        Log.i(TAG,"refreshToken")
        val token  = getTokenFromFirebase()
        token?.let {
            Log.d(TAG,"Refreshed token: $token")
            saveToken(token)
        }
    }

    private suspend fun getTokenFromFirebase(): String? {
        Log.i(TAG,"getTokenFromFirebase")
        return suspendCoroutine { cont ->
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG,"Token from firebase: ${task.result}")
                        saveToken(task.result)
                        cont.resume(task.result)
                    }
                    else cont.resume(null)
                }
        }
    }
}