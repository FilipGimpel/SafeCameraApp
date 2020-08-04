package com.gimpel.safecamera.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @ColumnInfo(name = "file_name") val fileName: String,
    @ColumnInfo(name = "encrypted_thumbnail_path") val encryptedThumbnailPath: String,
    @ColumnInfo(name = "encrypted_photo_path") val encryptedPhotoPath: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
