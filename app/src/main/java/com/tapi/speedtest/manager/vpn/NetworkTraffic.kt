package com.tapi.speedtest.manager.vpn


import android.util.Log
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.`object`.NetworkTrafficResult
import com.tapi.speedtest.core.Terminal
import com.tapi.speedtest.util.Utils

class NetworkTraffic {
    companion object {
        val terminal = Terminal()
    }

    suspend fun mesure(listIP: List<IP>): List<NetworkTrafficResult> {
        val listNetwork = mutableListOf<NetworkTrafficResult>()
        listIP.forEach {
            val item = terminal.ping(it.address)
            listNetwork.add(
                NetworkTrafficResult(
                    Utils.getIPAddress(true),
                    item.destination,
                    item.calculateAverage()
                )
            )
        }
        return listNetwork

    }

    suspend fun mesure(dest: IP): NetworkTrafficResult {
        val item = terminal.ping(dest.address)
        return NetworkTrafficResult(
            Utils.getIPAddress(true),
            item.destination,
            item.calculateAverage()
        )
    }


}