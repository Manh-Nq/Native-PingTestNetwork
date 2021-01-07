package com.tapi.internetprotocoldemo.`object`

import android.util.Log
import com.tapi.speedtest.`object`.ICMPReply

class ICMPStatistics(
    var listICMPRequest: List<ICMPReply> = ArrayList(),
    var destination: String = "", var currentTime: Long = 0L
) {

    fun filterRatio(): Int {
        var received = 0
        var lost = 0
        for (icmpReply in listICMPRequest) {
            Log.d("TAG", "ICMPPackets :  filterList: ${icmpReply.toString()}")
            if (icmpReply.isRequest) {
                received++
            } else {
                lost++
            }
        }
        return (lost / listICMPRequest.size) * 100
    }


    fun calculateMaxTime(): Double {
        var maxTime = listICMPRequest[0].time.toDouble()
        if (listICMPRequest.isNotEmpty()) {
            for (icmpReply in listICMPRequest) {
                if (icmpReply.time.toDouble() > maxTime) {
                    maxTime = icmpReply.time.toDouble()
                }
            }
            return maxTime
        }
        return 0.0
    }

    fun calculateMinTime(): Double {
        var minTime = listICMPRequest[0].time.toDouble()
        if (listICMPRequest.isNotEmpty()) {
            for (icmpReply in listICMPRequest) {
                if (icmpReply.time.toDouble() < minTime) {
                    minTime = icmpReply.time.toDouble()
                }
            }
            return minTime
        }
        return 0.0

    }


    fun calculateAverage(): Int {
        if (listICMPRequest.isNotEmpty()) {
            var d = 0.0
            for (item in listICMPRequest) {
                if (item.isRequest) {
                    d += item.time.toDouble()
                } else {
                    d += (item.ttl.toDouble()) * 1000
                }
            }
            val size = listICMPRequest.size.toDouble()
            return (d / size).toInt()
        }
        return -1
    }
}