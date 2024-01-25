package com.example.daon

data class CalendarContent(
    var serialNum : Int, // 일련번호
    var date : String, // 날짜(년/월/일)
    var clinic : ArrayList<Clinic>, // 제목

    var hour : String, // 알람 시
    var minute : String, // 알람 분
    var alarm_code : Int, // 알람 요청코드

)
