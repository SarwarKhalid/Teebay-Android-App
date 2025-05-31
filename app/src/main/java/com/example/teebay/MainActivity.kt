package com.example.teebay

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.teebay.presentation.navigation.SetupNavigation
import com.example.teebay.presentation.theme.TeebayTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"onCreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TeebayTheme {
                SetupNavigation()
            }
        }
    }
}


