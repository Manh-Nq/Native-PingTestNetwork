package com.tapi.speedtest.manager

import android.util.Log
import com.tapi.internetprotocoldemo.`object`.ICMPStatistics
import com.tapi.speedtest.core.Terminal


class CMDManager {
    val terminal = Terminal()

    fun filterIPPerfect(listIP: List<String>):String {
        val listICMP = mutableListOf<ICMPStatistics>()

        listIP.map {
            listICMP.add(terminal.ping(it))
        }
        listICMP.forEach {
            Log.d(
                "TAG",
                "ICMPPackets filterIPPerfect: ${it.host}  - average ${it.average}ms  - lost ${it.lost}"
            )
        }

        return listICMP[0].host

    }

}