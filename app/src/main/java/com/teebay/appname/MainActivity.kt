package com.teebay.appname

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.teebay.appname.framework.Notification.TeebayNotificationManager.isNotificationPermissionGranted
import com.teebay.appname.presentation.navigation.SetupNavigation
import com.teebay.appname.presentation.theme.TeebayTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"onCreate")
        super.onCreate(savedInstanceState)
        getNotificationPermissionIfNeeded()
        enableEdgeToEdge()
        setContent {
            TeebayTheme {
                SetupNavigation()
            }
        }
    }

    private fun getNotificationPermissionIfNeeded() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            Log.i(TAG,"Notification permission granted: $isGranted")
        }

        if (!isNotificationPermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}


