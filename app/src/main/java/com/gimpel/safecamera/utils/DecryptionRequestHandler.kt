package com.gimpel.safecamera.utils

import android.graphics.Bitmap
import com.gimpel.safecamera.storage.UserRepository
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler
import java.io.IOException


class DecryptionRequestHandler(private val userRepository: UserRepository) : RequestHandler() {
    override fun canHandleRequest(data: Request): Boolean {
        // check ending with .crypt
        return data.uri.path!!.endsWith(ENCRYPTION_SUFFIX)
    }

    @Throws(IOException::class)
    override fun load(request: Request, networkPolicy: Int): Result {
        // do whatever is necessary here to load the image
        // important: you can only return a Result object
        // the constructor takes either a Bitmap or InputStream object, nothing else!
        val bitmap: Bitmap =
            decrypt(request.uri.path!!, userRepository.getAuthenticatedUser())

        // return the result with the bitmap and the source info
        return Result(bitmap, Picasso.LoadedFrom.DISK)
    }
}