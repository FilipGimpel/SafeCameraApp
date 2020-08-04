package com.gimpel.safecamera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gimpel.safecamera.storage.Photo
import com.gimpel.safecamera.storage.PhotoRepository

class PhotoDetailViewModel (
    private val photoRepository: PhotoRepository
) : ViewModel() {

    fun getPhotoById(id: Int) : LiveData<Photo> {
        return photoRepository.getById(id)
    }
}