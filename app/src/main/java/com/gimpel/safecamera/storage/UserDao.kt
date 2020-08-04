package com.gimpel.safecamera.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User where username = :username")
    suspend fun getByUsername(username: String): User?

    @Insert
    suspend fun insertAll(vararg users: User)
}