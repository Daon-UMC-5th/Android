package com.example.daon.conect

import com.example.daon.conect.calendar.CalendarService

class ServicePool {
    val daonService: DaonService = ApiClient.create(DaonService::class.java)
    val calendarService: CalendarService = ApiClient.create(CalendarService::class.java)
}
