package com.tapi.speedtest.`object`

class ICMPPackets(
    var listICMPRequest: List<ICMPRequest>? = null,
    var minimum: Double? = 0.0,
    var maximum: Double? = 0.0,
    var average: Int? = 0,
    var send: Int? = -1,
    var received: Int? = -1,
    var lost: Int? = -1,
    var ratioLost: Int? = -1
) {
}