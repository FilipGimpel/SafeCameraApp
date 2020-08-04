package com.gimpel.safecamera.storage

class PhotoRepository(private val appDatabase: AppDatabase, private val fileManager: FileManager) {
    fun getAllPhotos() = appDatabase.photoDao().getAll()
    suspend fun save(photoData: Photo) = appDatabase.photoDao().insertAll(photoData)
    suspend fun deleteAll() {
        appDatabase.photoDao().deleteAll()
        fileManager.deleteAllPhotos()
    }
    fun getById(id: Int) = appDatabase.photoDao().getById(id)
}