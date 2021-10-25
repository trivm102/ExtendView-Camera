package com.trivm.extendview.camera.extension

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri

fun Bitmap.rotate(rotationDegrees: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(rotationDegrees)
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}

fun getImageBitmapRotated(contentResolver: ContentResolver?, imageUri: Uri): Bitmap? {
    val bitmap = getBitmap(contentResolver, imageUri) ?: return null
    if (bitmap.width > bitmap.height) {
        return bitmap.rotate(90F)
    }
    return bitmap
}

fun getBitmap(contentResolver: ContentResolver?, fileUri: Uri?): Bitmap? {
    if (contentResolver != null && fileUri != null) {
        contentResolver.openInputStream(fileUri)?.let { inputStream ->
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            return bitmap
        } ?: run { return null }
    }
    return null
}