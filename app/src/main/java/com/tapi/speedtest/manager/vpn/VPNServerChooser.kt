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
        val listRs = mutableListOf<NetworkTrafficEntity>()
        for (ip in listIP) {
            val itemNetworkTraffic = vpnCacher.getIPOrNull(Utils.getIPAddress(true), ip.address)
            if (itemNetworkTraffic == null) {
                scanAllSaveToDB(ip)
                choose(listIP)
            } else {
                listRs.add(itemNetworkTraffic)
            }
        }
        listRs.sortBy { it.duration }
        return Utils.convertIP(listRs[0].destination)

    }

    suspend fun deleteAll() {
        vpnCacher.deleteAll()
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