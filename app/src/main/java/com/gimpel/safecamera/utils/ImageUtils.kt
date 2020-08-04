package com.gimpel.safecamera.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

fun resizeImage(file: File, scaleTo: Int = 1024) : File {
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(file.path, bmOptions)
    val photoW = bmOptions.outWidth
    val photoH = bmOptions.outHeight

    // Determine how much to scale down the image
    val scaleFactor = Math.min(photoW / scaleTo, photoH / scaleTo)

    bmOptions.inJustDecodeBounds = false
    bmOptions.inSampleSize = scaleFactor

    val resized = BitmapFactory.decodeFile(file.path, bmOptions) ?: return File("")
    val resizedFile = File(file.path.replace(".jpg", "_resized.jpg"))
    resizedFile.outputStream().use {
        resized.compress(Bitmap.CompressFormat.JPEG, 75, it)
        resized.recycle()
    }
    return resizedFile
}