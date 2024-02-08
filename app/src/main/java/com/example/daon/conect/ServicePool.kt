package com.example.daon.conect

class ServicePool {
    val daonService: DaonService = ApiClient.create(DaonService::class.java)
}
