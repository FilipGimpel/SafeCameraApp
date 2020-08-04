package com.gimpel.safecamera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gimpel.safecamera.utils.SingleLiveEvent
import com.gimpel.safecamera.storage.Photo
import com.gimpel.safecamera.storage.PhotoRepository
import com.gimpel.safecamera.storage.UserRepository
import com.gimpel.safecamera.utils.encrypt
import com.gimpel.safecamera.utils.resizeImage
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraViewModel (
    private val photoRepository: PhotoRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val photoSuccessfullyCaptured : LiveData<Boolean> by lazy {
        SingleLiveEvent(false)
    }

    private fun setPhotoCaptureResult(result: Boolean) {
        (photoSuccessfullyCaptured as? SingleLiveEvent)?.postValue(result)
    }

    fun processPhotoFile(path: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val userPasswordHash = userRepository.getAuthenticatedUser()

            val photo = File(path)
            val thumbnail = resizeImage(photo)
            val photoEncrypted = encrypt(photo, userPasswordHash)
            val thumbnailEncrypted = encrypt(thumbnail, userPasswordHash)

            photo.delete()
            thumbnail.delete()

            photoRepository.save(Photo(
                SimpleDateFormat("HH:mm:ss",
                    Locale.getDefault()).format(Calendar.getInstance().time),
                thumbnailEncrypted.path,
                photoEncrypted.path))

            setPhotoCaptureResult(true)
        }
    }
}