package com.tapi.speedtest.manager.vpn

import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.database.entity.NetworkTrafficResult
import com.tapi.speedtest.util.Utils

class VPNServerChooser {
    companion object {
        val vpnCacher = VPNCacher()
    }


    suspend fun choose(listIP: List<IP>): IP {
        val listICMP = vpnCacher.getAllTable()
        var ipPerfect = listICMP[0]

        listICMP.forEach {
            if (it.validateThreshold < ipPerfect.validateThreshold) {
                ipPerfect = it
            }
        }
        if (listIP.contains(ipPerfect.destination) && (System.currentTimeMillis() - ipPerfect.validateThreshold) <= Constance.TIME_CONFIG) {
            return Utils.convertIP(ipPerfect.destination)
        } else {
            scanAndFilterList(listIP)
        }
        return IP()
    }


    private suspend fun scanAndFilterList(listIP: List<IP>) {
        val rs = scanAllIP(listIP)
        if (rs) {
            choose(listIP)
        }
    }

    suspend fun scanAllIP(listIP: List<IP>): Boolean {
        try {
            listIP.map {
                val itemICMP = NetworkTraffic.terminal.ping(it.ip)
                val icmpEntity = NetworkTrafficResult()
                icmpEntity.destination = itemICMP.destination
                icmpEntity.host = "1.1.1.1"
                icmpEntity.duration = itemICMP.calculateAverage()
                icmpEntity.validateThreshold = itemICMP.currentTime
                vpnCacher.saveResult(icmpEntity)
            }
            return true

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


}