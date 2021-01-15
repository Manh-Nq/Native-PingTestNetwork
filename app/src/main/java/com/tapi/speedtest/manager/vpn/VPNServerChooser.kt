package com.tapi.speedtest.manager.vpn

import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.database.entity.NetworkTrafficEntity


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
                scanAndSaveToDB(ip)
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


    private suspend fun scanAndSaveToDB(ip: IP) {
        val networkTrafficResult = networkTraffic.measure(ip)
        val networkEntity = Utils.parseNetworkTrafficEntity(networkTrafficResult)
        vpnCacher.saveResult(networkEntity)
    }
}