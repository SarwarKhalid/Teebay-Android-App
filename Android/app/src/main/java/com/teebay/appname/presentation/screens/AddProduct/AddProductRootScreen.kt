package com.teebay.appname.presentation.screens.AddProduct

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.teebay.appname.framework.Util.ImagePickerManager
import com.teebay.appname.presentation.screens.AddProduct.nestedscreens.CategorySelectScreen
import com.teebay.appname.presentation.screens.AddProduct.nestedscreens.DescriptionScreen
import com.teebay.appname.presentation.screens.AddProduct.nestedscreens.ImageUploadScreen
import com.teebay.appname.presentation.screens.AddProduct.nestedscreens.PriceInputScreen
import com.teebay.appname.presentation.screens.AddProduct.nestedscreens.ProductSummaryScreen
import com.teebay.appname.presentation.screens.AddProduct.nestedscreens.ProductTitleScreen

@Composable
fun AddProductRootScreen(
    uiState: AddProductUiState,
    onEvent: (AddProductEvent) -> Unit,
    onNavigateToHome: () -> Unit
) {
    var step by remember { mutableStateOf(AddProductStep.TITLE) }
    val allCategories = listOf(
        "electronics",
        "furniture",
        "home_appliances",
        "toys",
        "sporting_goods",
        "outdoor"
    ) //Placeholder for categories
    val context = LocalContext.current
    val imagePickerManager = remember { ImagePickerManager(context) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imagePickerManager.getFile()?.toUri()?.let {
                onEvent(AddProductEvent.ImageSelected(it))
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onEvent(AddProductEvent.ImageSelected(it)) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text("Add a product")
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = {
                val totalSteps = AddProductStep.entries.size
                val currentStepIndex = step.ordinal + 1 // Steps are 0-based
                currentStepIndex / totalSteps.toFloat()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (step) {
            AddProductStep.TITLE -> ProductTitleScreen(
                title = uiState.title,
                onTitleChange = { onEvent(AddProductEvent.TitleChanged(it)) },
                onNext = { step = AddProductStep.CATEGORIES }
            )

            AddProductStep.CATEGORIES -> CategorySelectScreen(
                selectedCategories = uiState.categories,
                allCategories = allCategories,
                onCategoryToggle = { onEvent(AddProductEvent.CategoryToggled(it)) },
                onNext = { step = AddProductStep.DESCRIPTION },
                onBack = { step = AddProductStep.entries[step.ordinal - 1] }
            )

            AddProductStep.DESCRIPTION -> DescriptionScreen(
                description = uiState.description,
                onDescriptionChange = { onEvent(AddProductEvent.DescriptionChanged(it)) },
                onNext = { step = AddProductStep.IMAGE },
                onBack = { step = AddProductStep.entries[step.ordinal - 1] }
            )

            AddProductStep.IMAGE -> ImageUploadScreen(
                imageUri = uiState.imageUri,
                onPickImageClick = { galleryLauncher.launch("image/*") },
                onCaptureImageClick = {
                    imagePickerManager.createNewImageFile()?.let{ uri ->
                        cameraLauncher.launch(uri)
                    }
                },
                onNext = { step = AddProductStep.PRICE },
                onBack = { step = AddProductStep.entries[step.ordinal - 1] }
            )

            AddProductStep.PRICE -> PriceInputScreen(
                purchasePrice = uiState.purchasePrice,
                rentPrice = uiState.rentPrice,
                rentOption = uiState.rentOption,
                onPurchasePriceChange = { onEvent(AddProductEvent.PurchasePriceChanged(it)) },
                onRentPriceChange = { onEvent(AddProductEvent.RentPriceChanged(it)) },
                onRentOptionChange = { onEvent(AddProductEvent.RentOptionChanged(it)) },
                onNext = { step = AddProductStep.SUMMARY },
                onBack = { step = AddProductStep.entries[step.ordinal - 1] }
            )

            AddProductStep.SUMMARY -> ProductSummaryScreen(
                addProductStatus = uiState.addProductStatus,
                title = uiState.title,
                categories = uiState.categories,
                description = uiState.description,
                imageUri = uiState.imageUri,
                purchasePrice = uiState.purchasePrice,
                rentPrice = uiState.rentPrice,
                rentOption = uiState.rentOption,
                onSubmit = {
                    onEvent(AddProductEvent.Submit)
                },
                onBack = { step = AddProductStep.entries[step.ordinal - 1] },
                onNavigateToHome = onNavigateToHome
            )
        }
    }
}
