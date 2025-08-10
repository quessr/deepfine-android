package com.quessr.deepfineandroid.core.secure

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.encoding.ExperimentalEncodingApi

@Singleton
class DbKeyStore @Inject constructor(@ApplicationContext context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_keystore",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @OptIn(ExperimentalEncodingApi::class)
    fun getOrCreatePassphraseString(): String {
        val saved = prefs.getString("db_key_str", null)
        if (saved != null) return saved

        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val rnd = SecureRandom()
        val newKey = buildString {
            repeat(56) { append(alphabet[rnd.nextInt(alphabet.length)]) }
        }
        prefs.edit().putString("db_key_str", newKey).apply()
        return newKey
    }
}