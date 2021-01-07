package com.tapi.speedtest.manager.vpn


import com.tapi.speedtest.MyApp
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.core.Terminal
import com.tapi.speedtest.database.entity.NetworkTrafficResult

class NetworkTraffic {
    companion object {
        val terminal = Terminal()
    }

    suspend fun mesure(listIP: List<IP>): List<NetworkTrafficResult>? {
        val listICMP = MyApp.terminalDB.terminalDAO.getAllICMP()
        for (index in listICMP.indices) {
            if (listICMP[index].destination == listIP[index].ip) {
                return listICMP
            }
        }
        return null
    }

    suspend fun mesure(dest: IP): NetworkTrafficResult? {
        val listICMP = MyApp.terminalDB.terminalDAO.getAllICMP()
        if (listICMP.isNotEmpty()) {
            listICMP.forEach {
                if (it.destination == dest.ip) {
                    return it
                }
            }
        }
        return null
    }


}