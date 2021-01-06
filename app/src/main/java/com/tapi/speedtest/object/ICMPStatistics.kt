package com.tapi.internetprotocoldemo.`object`

import com.tapi.speedtest.`object`.ICMPPackets

class ICMPStatistics(
    var listICMPRequest: List<ICMPPackets>,
    var minimum: Double = 0.0,
    var maximum: Double = 0.0,
    var average: Int = 0,
    var send: Int = -1,
    var received: Int = -1,
    var lost: Int = -1,
    var ratioLost: Int = -1,
    var result: String = "",
    var host: String = ""
) {
}