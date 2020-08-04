package com.gimpel.safecamera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gimpel.safecamera.storage.Photo
import com.gimpel.safecamera.storage.PhotoRepository
import androidx.lifecycle.viewModelScope
import com.gimpel.safecamera.utils.encrypt
import com.gimpel.safecamera.utils.resizeImage
import kotlinx.coroutines.launch
import java.io.File

class PhotoGridViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    fun getAllPhotos() : LiveData<List<Photo>> {
        return photoRepository.getAllPhotos()
    }

    fun deleteAllPhotos() = viewModelScope.launch {
        photoRepository.deleteAll()
    }
}
