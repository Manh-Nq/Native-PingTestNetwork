package com.tapi.speedtest.manager.vpn

import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.database.entity.NetworkTrafficEntity
import com.tapi.speedtest.util.Utils

class VPNServerChooser {

    companion object {
        val vpnCacher = VPNCacher()
        val networkTraffic = NetworkTraffic()
    }


    suspend fun choose(listIP: List<IP>): IP {
        var ipPerfect = IP()
        for (ip in listIP) {
            val itemNetworkTraffic = vpnCacher.getIPOrNull("1.1.1.1", ip.ip)
            ipPerfect = if (itemNetworkTraffic == null) {
                scanAllSaveToDB(ip)
            } else {
                ip
            }
        }
        return ipPerfect

    }

    private suspend fun scanAllSaveToDB(ip: IP): IP {
        val networkTrafficResult = networkTraffic.mesure(ip)
        val networkTrafficEntity = NetworkTrafficEntity()
        networkTrafficEntity.destination = networkTrafficResult.dest
        networkTrafficEntity.host = networkTrafficResult.host
        networkTrafficEntity.duration = networkTrafficResult.duration
        networkTrafficEntity.validateThreshold = System.currentTimeMillis()
        vpnCacher.saveResult(networkTrafficEntity)
        return Utils.convertIP(networkTrafficEntity.destination)
    }

}