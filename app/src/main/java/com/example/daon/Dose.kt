package com.example.daon

data class Dose(
    var serialNum : Int, // 일련번호
    var date : String, // 삼시세끼
    var medicine : String, // 약
    var alarm : Boolean, // 알람여부
    var alarmTime : String? // 알람시간
)
