package com.example.daon

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    //remove 함수는 splash에서 사용하여 초기화
//    fun setStatement(key:String, defValue: String){
//        preferences.edit().putString(key,defValue).apply()
//    }
//    fun getStatement(key:String,defValue: String):String{
//        return preferences.getString(key,defValue).toString()
//    }
//    fun removeStatement(key:String){
//        preferences.edit().remove(key).apply()
//    }
//    fun setDate(key:String, defValue: String){
//        preferences.edit().putString(key,defValue).apply()
//    }
//    fun getDate(key: String,defValue: String): String{
//        return preferences.getString(key,defValue).toString()
//    }
//    fun removeDate(key: String){
//        preferences.edit().remove(key).apply()
//    }
    fun setDate(key:String, defValue: Int){
        preferences.edit().putInt(key,defValue).apply()
    }
    fun getDate(key: String,defValue: Int): Int{
        return preferences.getInt(key,defValue)
    }
    fun removeDate(key: String){
        preferences.edit().remove(key).apply()
    }
//    fun setCalendarStatement(key:String, defValue: String){
//        preferences.edit().putString(key, defValue).apply()
//    }
//    fun getCalendarStatement(key:String, defValue: String):String{
//        return preferences.getString(key,defValue).toString()
//    }
//    fun removeCalendarStatement(key:String){
//        preferences.edit().remove(key).apply()
//    }
//    fun setDiaryCalendarStatement(key:String, defValue: String){
//        preferences.edit().putString(key, defValue).apply()
//    }
//    fun getDiaryCalendarStatement(key:String, defValue: String):String{
//        return preferences.getString(key,defValue).toString()
//    }
//    fun removeDiaryCalendarStatement(key:String){
//        preferences.edit().remove(key).apply()
//    }
    fun setDiaryStatement(key:String, defValue: String){
        preferences.edit().putString(key,defValue).apply()
    }
    fun getDiaryStatement(key: String,defValue: String):String{
        return preferences.getString(key,defValue).toString()
    }
    fun removeDiaryStatement(key:String){
        preferences.edit().remove(key).apply()
    }
}