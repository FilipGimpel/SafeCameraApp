package com.gimpel.safecamera.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {
    @Query("SELECT * FROM Photo")
    fun getAll(): LiveData<List<Photo>>

    @Query("SELECT * FROM Photo where uid = :id")
    fun getById(id: Int): LiveData<Photo>

    @Insert
    suspend fun insertAll(vararg photos: Photo)

    @Query("DELETE FROM Photo")
    suspend fun deleteAll()
}
