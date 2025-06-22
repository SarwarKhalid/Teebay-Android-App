package com.teebay.appname.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NextAndBackButton(onNext: () -> Unit, onNextText: String, onBack: () -> Unit, onBackText: String, onNextEnabled: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedButton(
            onClick = onBack
        ) {
            Text(onBackText)
        }
        Button(onClick = onNext, enabled = onNextEnabled) {
            Text(onNextText)
        }
    }
}