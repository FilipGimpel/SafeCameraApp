package com.gimpel.safecamera.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

const val SALT = "SUL"
const val SHA1 = "SHA-1"
const val AES = "AES"
const val UTF8 = "UTF-8"
const val MD5 = "MD5"
const val US_ASCII = "US-ASCII"
const val ENCRYPTION_SUFFIX = ".crypt"

@Throws(
    IOException::class,
    NoSuchAlgorithmException::class,
    NoSuchPaddingException::class,
    InvalidKeyException::class
)
fun encrypt(file: File, password: String): File {
    val inputStream: InputStream = FileInputStream(file.path)
    val fos = FileOutputStream(file.path + ENCRYPTION_SUFFIX)
    var key: ByteArray? = (SALT + password).toByteArray(charset(
        UTF8
    ))
    val sha: MessageDigest = MessageDigest.getInstance(SHA1)
    key = sha.digest(key)
    key = Arrays.copyOf(key, 16)
    val sks = SecretKeySpec(key, AES)
    val cipher: Cipher = Cipher.getInstance(AES)
    cipher.init(Cipher.ENCRYPT_MODE, sks)
    val cos = CipherOutputStream(fos, cipher)
    var b: Int
    val d = ByteArray(8)
    while (inputStream.read(d).also { b = it } != -1) {
        cos.write(d, 0, b)
    }
    cos.flush()
    cos.close()
    inputStream.close()
    return File(file.path + ENCRYPTION_SUFFIX)
}

@Throws(
    IOException::class,
    NoSuchAlgorithmException::class,
    NoSuchPaddingException::class,
    InvalidKeyException::class
)
fun decrypt(path: String?, password: String): Bitmap {
    val fis = FileInputStream(path)
    var key: ByteArray? = (SALT + password).toByteArray(charset(UTF8))
    val sha = MessageDigest.getInstance(SHA1)
    key = sha.digest(key)
    key = Arrays.copyOf(key, 16)
    val sks = SecretKeySpec(key, AES)
    val cipher = Cipher.getInstance(AES)
    cipher.init(Cipher.DECRYPT_MODE, sks)
    val cis = CipherInputStream(fis, cipher)
    val bitmap: Bitmap = BitmapFactory.decodeStream(cis)
    cis.close()
    return bitmap
}

fun md5(s: String): String {
    val digest: MessageDigest
    try {
        digest = MessageDigest.getInstance(MD5)
        digest.update(s.toByteArray(Charset.forName(US_ASCII)), 0, s.length)
        val magnitude = digest.digest()
        val bi = BigInteger(1, magnitude)
        return java.lang.String.format("%0" + (magnitude.size shl 1) + "x", bi)
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}