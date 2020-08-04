package com.gimpel.safecamera.koin

import androidx.room.Room
import com.gimpel.safecamera.storage.AppDatabase
import com.gimpel.safecamera.storage.FileManager
import com.gimpel.safecamera.storage.PhotoRepository
import com.gimpel.safecamera.storage.UserRepository
import com.gimpel.safecamera.utils.DecryptionRequestHandler
import com.gimpel.safecamera.viewmodel.CameraViewModel
import com.gimpel.safecamera.viewmodel.LoginViewModel
import com.gimpel.safecamera.viewmodel.PhotoDetailViewModel
import com.gimpel.safecamera.viewmodel.PhotoGridViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { PhotoGridViewModel(get()) }
    viewModel { CameraViewModel(get(), get()) }
    viewModel { PhotoDetailViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    single { UserRepository(get(), get()) }
    single { PhotoRepository(get(), get()) }
    single { FileManager(get()) }
//    single { DecryptionRequestHandler(get()) }
    single {
        var picassoBuilder = Picasso.Builder(get())
        picassoBuilder.addRequestHandler(DecryptionRequestHandler(get()))
        picassoBuilder.build() }
    single {
        Room.databaseBuilder(
        androidApplication(),
        AppDatabase::class.java, "database-name").build()
    }
}