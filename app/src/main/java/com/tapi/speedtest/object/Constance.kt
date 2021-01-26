package com.tapi.speedtest.`object`

import com.tapi.speedtest.ui.linespeedview.MyPoint

object Constance {
    const val TEXT_SIZE_CONNECTVIEW = 25f
    const val MAX_ALPHA = 255
    const val MAX_MARKS = 68
    const val TIME_CONFIG = 30 * 60 * 1000L
    const val REQUEST_TIME_OUT = "Request timed out"
    const val COUNT_REQUEST = 4
    const val PING_CMD = "ping -c 1 -w 5 "
    const val TTL_DEFAULT = "120000"
    const val URI_SPEED_TEST_UPLOAD = "http://ipv4.ikoula.testdebit.info/"
    const val URI_SPEED_TEST_DOWNLOAD_1M = "http://ipv4.ikoula.testdebit.info/1M.iso"
    const val MAX_ANGLE = 260.03552f
    val LIST_SCALE = arrayListOf(
        0.8f, 0.81f, 0.82f,
        0.83f, 0.84f, 0.85f, 0.86f, 0.87f, 0.88f, 0.9f,
        0.91f, 0.92f, 0.93f, 0.94f, 0.95f, 0.96f, 0.97f, 0.98f, 0.99f, 1f, 0.99f,
        0.98f, 0.97f, 0.96f, 0.95f, 0.94f, 0.93f,
        0.92f, 0.91f, 0.89f, 0.88f, 0.87f,
        0.86f, 0.85f, 0.84f, 0.83f, 0.82f,
        0.81f, 0.8f, 0.79f
    )
}
enum class NetWork(var i: Int) {
    TYPE_INTERNET(1),
    TYPE_WIFI(2),
    TYPE_UNCONNECTION(0)
}