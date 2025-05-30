package com.example.teebay.presentation.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.teebay.core.model.Result
import kotlinx.coroutines.flow.StateFlow

private val TAG = "LoginScreen"

private val LOGIN_FAIL_MESSAGE = "Login failed, please try again."

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    onNavigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onEvent(LoginEvent.Submit) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.email.isNotBlank() && uiState.password.isNotBlank()
        ) {
            Text("Login")
        }

        uiState.loginResult?.let { result ->
            LaunchedEffect(key1 = result) {
                Log.i(TAG,"Login: $result")
            }
            when(result) {
                is Result.Success -> {
                    onNavigateToHome()
                }
                is Result.Failure -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(LOGIN_FAIL_MESSAGE)
                }
            }
        }
    }
}