package com.tapi.speedtest.`object`

object Constance {
    const val TIME_CONFIG = 30 * 60 * 1000L
}

enum class NetWork(var i: Int) {
    TYPE_INTERNET(1),
    TYPE_WIFI(2),
    TYPE_UNCONNECTION(0)
}