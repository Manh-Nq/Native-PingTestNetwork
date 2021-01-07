package com.tapi.speedtest.util

import com.tapi.speedtest.`object`.IP

object Utils {


    fun convertIP(ip: String): IP {
        return IP(ip)
    }
}