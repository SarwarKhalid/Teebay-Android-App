package com.example.teebay.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RentOptionSelector(
    rentOption: String,
    onRentOptionChange: (String) -> Unit
) {
    listOf("day", "hour").forEach {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            RadioButton(
                selected = rentOption == it,
                onClick = { onRentOptionChange(it) }
            )
            Text(it)
        }
    }
}