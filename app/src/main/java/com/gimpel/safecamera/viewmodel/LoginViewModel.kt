package com.gimpel.safecamera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gimpel.safecamera.storage.User
import com.gimpel.safecamera.storage.UserRepository
import com.gimpel.safecamera.utils.SingleLiveEvent
import com.gimpel.safecamera.utils.md5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val defaultUser = User("admin", md5("1234"))

    val authenticatedUser : LiveData<User> by lazy {
        SingleLiveEvent<User>()
    }

    // vm are lazily initialized so init won't be called unless you access vm property in fragment
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (userRepository.getByUsername(defaultUser.username) == null) {
                    userRepository.save(defaultUser)
                }
            }
        }
    }

    fun login(login: String, password: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val user = userRepository.getByUsername(login)

            if (user != null && user.passwordHash == md5(password)) {
                userRepository.setAuthenticatedUserHash(user.passwordHash)
                setAuthenticatedUser(user)
            } else {
                setAuthenticatedUser(null)
            }
        }
    }

    private fun setAuthenticatedUser(user: User?) {
        (authenticatedUser as? MutableLiveData)?.postValue(user)
    }
}