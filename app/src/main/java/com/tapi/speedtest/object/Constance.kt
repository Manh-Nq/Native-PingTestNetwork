package com.tapi.speedtest.`object`

object Constance {
    const val MAX_ALPHA=255
    const val MAX_MARKS = 68
    const val TIME_CONFIG = 30 * 60 * 1000L
    const val REQUEST_TIME_OUT = "Request timed out"
    const val COUNT_REQUEST = 4
    const val PING_CMD = "ping -c 1 -w 5 "
    const val TTL_DEFAULT = "120000"
    const val URI_SPEED_TEST_UPLOAD = "http://ipv4.ikoula.testdebit.info/"
    const val URI_SPEED_TEST_DOWNLOAD_1M = "http://ipv4.ikoula.testdebit.info/1M.iso"
    const val MAX_ANGLE =260.03552f
}
enum class NetWork(var i: Int) {
    TYPE_INTERNET(1),
    TYPE_WIFI(2),
    TYPE_UNCONNECTION(0)
}