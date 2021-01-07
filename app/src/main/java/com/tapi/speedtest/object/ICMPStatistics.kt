package com.tapi.internetprotocoldemo.`object`

import android.util.Log
import com.tapi.speedtest.`object`.ICMPReply

class ICMPStatistics(
    var listICMPRequest: List<ICMPReply>,
    var destination: String
) {

    private fun filterList() {
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
        val ratio = (lost / listICMPRequest.size) * 100
    }


    private fun calculateMaxTime(): Double {
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

    private fun calculateMinTime(): Double {
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


    private fun calculateAverage(): Int {
        if (listICMPRequest.isNotEmpty()) {
            var d = 0.0
            for (item in listICMPRequest) {
                d += item.time.toDouble()
            }
            val size = listICMPRequest.size.toDouble()
            return (d / size).toInt()
        }
        return -1
    }
}