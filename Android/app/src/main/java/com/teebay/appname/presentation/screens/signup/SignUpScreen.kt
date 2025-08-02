package com.teebay.appname.presentation.screens.signup

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.teebay.appname.core.model.Result

private val TAG = "SignUpScreen"

private val SIGNUP_FAIL_MESSAGE = "Signup failed, please try again."

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onEvent: (SignUpEvent) -> Unit,
    onNavigateToHome: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Sign Up", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEvent(SignUpEvent.EmailChanged(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = { onEvent(SignUpEvent.FirstNameChanged(it)) },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = { onEvent(SignUpEvent.LastNameChanged(it)) },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.address,
            onValueChange = { onEvent(SignUpEvent.AddressChanged(it)) },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onEvent(SignUpEvent.PasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { onEvent(SignUpEvent.Submit) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isSignUpButtonEnabled(uiState)
        ) {
            Text("Sign Up")
        }

        uiState.signupResult?.let { result ->
            LaunchedEffect(key1 = result) {
                Log.i(TAG, "Signup: $result")
            }
            when (result) {
                is Result.Success -> {
                    onNavigateToHome()
                }
                is Result.Failure -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(SIGNUP_FAIL_MESSAGE)
                }
            }
        }
    }
}

private fun isSignUpButtonEnabled(uiState: SignUpUiState): Boolean =
    with(uiState) {
        email.isNotBlank() &&
                firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                address.isNotBlank() &&
                password.isNotBlank()
    }

