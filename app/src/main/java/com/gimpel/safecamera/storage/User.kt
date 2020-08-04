package com.gimpel.safecamera.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password_hash") val passwordHash: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}