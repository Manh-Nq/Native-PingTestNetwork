package com.tapi.speedtest.manager.vpn


import android.util.Log
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.`object`.NetworkTrafficResult
import com.tapi.speedtest.core.Terminal

class NetworkTraffic {
    companion object {
        val terminal = Terminal()
    }

    suspend fun mesure(listIP: List<IP>): List<NetworkTrafficResult> {
        val listNetwork = mutableListOf<NetworkTrafficResult>()
        listIP.forEach {
            val item = terminal.ping(it.ip)
            listNetwork.add(
                NetworkTrafficResult(
                    "1.1.1.1",
                    item.destination,
                    item.calculateAverage()
                )
            )
        }
        return listNetwork

    }

    suspend fun mesure(dest: IP): NetworkTrafficResult {
        val item = terminal.ping(dest.ip)
        item.listICMPRequest.forEach {
            Log.d("TAG", "NManhhh: $it")
        }
        return NetworkTrafficResult("1.1.1.1", item.destination, item.calculateAverage())
    }


}