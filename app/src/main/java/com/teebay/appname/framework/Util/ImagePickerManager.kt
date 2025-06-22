package com.teebay.appname.framework.Util

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

private val TAG = "ImagePickerManager"

class ImagePickerManager(private val context: Context) {

    private var currentImageFile: File? = null

    /**
     * Creates a new temporary file and deletes the previous one (if exists).
     */
    fun createNewImageFile(): Uri? {
        return runCatching {
            // Delete old image file if it exists
            currentImageFile?.takeIf { it.exists() }?.delete()
            // Create new file
            val newFile = File.createTempFile(
                "product_image_${System.currentTimeMillis()}",
                ".jpg",
                context.cacheDir
            )
            currentImageFile = newFile

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                newFile
            )
        }.onFailure {
            Log.e(TAG,it.toString())
        }.getOrNull()
    }

    /**
     * Get the latest file.
     */
    fun getFile(): File? = currentImageFile
}