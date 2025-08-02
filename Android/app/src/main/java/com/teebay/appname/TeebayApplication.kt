package com.teebay.appname

import android.app.Application
import com.teebay.appname.core.domain.TokenUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "TeebayApplication"

@HiltAndroidApp
class TeebayApplication : Application() {

    @Inject
    lateinit var tokenUseCase: TokenUseCase

    override fun onCreate() {
        super.onCreate()
        //Refresh FCM token on app start
        CoroutineScope(Dispatchers.Default).launch {
            tokenUseCase.refreshToken()
        }
    }
}