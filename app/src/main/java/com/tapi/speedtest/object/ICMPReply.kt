package com.tapi.speedtest.`object`

class ICMPReply(
    var timeToLive: String = "",
    var time: String = "",
    var bytes: String = "",
    var isRequest: Boolean = false
) {
    override fun toString(): String {
        return " ttl: $timeToLive time:$time bytes : $bytes - $isRequest"
    }
}