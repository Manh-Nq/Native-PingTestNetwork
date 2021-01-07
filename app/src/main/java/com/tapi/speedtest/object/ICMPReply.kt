package com.tapi.speedtest.`object`

class ICMPReply(
    var ttl: String = "",
    var time: String = "",
    var bytes: String = "",
    var isRequest: Boolean = false
) {
    override fun toString(): String {
        return " ttl: $ttl time:$time bytes : $bytes - $isRequest"
    }
}