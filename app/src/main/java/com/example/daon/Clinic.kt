package com.example.daon

data class Clinic(
    var serialNum : Int, // 일련번호
    var hospital : String, // 병원
    var content : String, // 내용
    var alarm : Boolean, // 알람여부
    var alarmTime : String? // 알람시간
)
