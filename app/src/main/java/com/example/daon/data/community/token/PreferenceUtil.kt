package com.example.daon.data.community.token

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String?): String {
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
    fun saveUserNickname(userNickname: String) {
        setString("user_nickname", userNickname)
    }
    fun getUserNickname(): String? {
        return getString("user_nickname", null)
    }
    fun saveUserIntro(userIntro: String) {
        setString("user_intro", userIntro)
    }
    fun getUserIntro(): String? {
        return getString("user_intro", null)
    }
    fun savePostId(postId: Int) {
       prefs.edit().putInt("postId",postId).apply()
    }
    fun getPostId(): Int {
        return prefs.getInt("postId", 1)
    }
    fun saveBoardType(boardType: String) {
        setString("boardType", boardType)
    }
    fun getBoardType(): String? {
        return getString("boardType", null)
    }
}
