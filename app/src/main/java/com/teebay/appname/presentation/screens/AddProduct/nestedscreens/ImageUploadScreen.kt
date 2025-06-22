package com.teebay.appname.presentation.screens.AddProduct.nestedscreens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.teebay.appname.presentation.components.NextAndBackButton

@Composable
fun ImageUploadScreen(
    imageUri: Uri?,
    onPickImageClick: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onPickImageClick) {
            Text("Select Product Image")
        }
        Spacer(Modifier.height(8.dp))
        imageUri?.let {
            AsyncImage(model = it, contentDescription = "Selected Image", modifier = Modifier.size(200.dp))
        }
        Spacer(Modifier.height(16.dp))
        NextAndBackButton(
            onNext = onNext,
            onNextText = "Next",
            onBack = onBack,
            onBackText = "Back",
            onNextEnabled = imageUri != null
        )
    }
}
