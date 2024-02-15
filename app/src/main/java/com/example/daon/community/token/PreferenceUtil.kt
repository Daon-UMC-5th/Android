package com.example.daon.community.token

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun saveToken(token: String) {
        setString("token", token)
    }

    fun getToken(): String? {
        return getString("token", null.toString())
    }

    fun saveFavoriteState(itemId: Int, isFavorite: Boolean) {
        prefs.edit().putBoolean("favorite_$itemId", isFavorite).apply()
    }

    fun getFavoriteState(itemId: Int): Boolean {
        return prefs.getBoolean("favorite_$itemId", false)
    }
}
