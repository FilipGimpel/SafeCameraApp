package com.gimpel.safecamera.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class UserRepository(private val appDatabase: AppDatabase, private val context: Context) {
    suspend fun save(vararg users: User) = appDatabase.userDao().insertAll(*users)
    suspend fun getByUsername(username: String) = appDatabase.userDao().getByUsername(username)

    private val sharedPrefs = "sharedPrefs"
    private val authenticatedUser = "authenticatedUser"

    fun setAuthenticatedUserHash(passwordHash : String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(sharedPrefs, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(authenticatedUser, passwordHash)
        editor.apply()
    }

    fun getAuthenticatedUser(): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(sharedPrefs, MODE_PRIVATE)
        return sharedPreferences.getString(authenticatedUser, "") ?: ""
    }

}