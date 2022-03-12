package com.esmaeel.check24_challenge.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsLocalDataSource @Inject constructor(private val prefs: SharedPreferences) {

    /**
     * No need to use DataStore or EncryptedSharedPreferences in this scenario for simplicity
     */

    fun addToFavorites(productId: Int) =
        prefs.edit().putInt(productId.toString(), productId).apply()

    fun removeFromFavorites(productId: Int) = prefs.edit().remove(productId.toString()).apply()

    fun isInFavorites(productId: Int): Boolean {
        val exist = prefs.getInt(productId.toString(), -1)
        return exist != -1
    }
}