package com.tapi.speedtest.manager.vpn


import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.`object`.NetworkTrafficResult
import com.tapi.speedtest.core.Terminal

class NetworkTraffic {
    companion object {
        val terminal = Terminal()
    }

    suspend fun measure(listIP: List<IP>): List<NetworkTrafficResult> {
        val listNetwork = mutableListOf<NetworkTrafficResult>()
        listIP.forEach {
            val item = terminal.ping(it.address)
            listNetwork.add(
                NetworkTrafficResult(
                    Utils.  getIPAddress(true),
                    item.destination,
                    item.calculateAverage()
                )
            )
        }
        return listNetwork

    }

    suspend fun measure(dest: IP): NetworkTrafficResult {
        val item = terminal.ping(dest.address)
        return NetworkTrafficResult(
            Utils.  getIPAddress(true),
            item.destination,
            item.calculateAverage()
        )
    }


}