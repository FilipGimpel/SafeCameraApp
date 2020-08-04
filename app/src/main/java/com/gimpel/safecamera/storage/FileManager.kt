package com.gimpel.safecamera.storage

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FileManager(private val context : Context) {
    private val photoFolderName = "photos"

    private fun getPhotoFolder() : File {
        /* /data/data/com.gimpel.safecamera/files + / + photos */
        val photosFolder = File(context.filesDir.path + "/" + photoFolderName)
        if (!photosFolder.exists()) photosFolder.mkdirs()
        return photosFolder
    }

    fun createTmpPhotoFile() : File {
        val timeStamp: String = SimpleDateFormat(
            "yyyy_MM_dd-HH_mm_ss-", Locale.getDefault()).format(Calendar.getInstance().time)
        return File.createTempFile(timeStamp, ".jpg", getPhotoFolder())
    }

    fun deleteAllPhotos() {
        getPhotoFolder().deleteRecursively()
    }
}