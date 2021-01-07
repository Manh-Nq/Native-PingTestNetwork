package com.tapi.speedtest.manager.vpn

import com.tapi.speedtest.MyApp
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.database.entity.NetworkTrafficResult

class VPNCacher {

    suspend fun saveResult(result: NetworkTrafficResult) {
        MyApp.terminalDB.terminalDAO.saveResult(result)
    }


    suspend fun getOrNull(host: IP, dest: IP) {
        MyApp.terminalDB.terminalDAO.getOrNull(host, dest)
    }


    suspend fun getAllTable(): List<NetworkTrafficResult> {
        return MyApp.terminalDB.terminalDAO.getAllICMP()
    }

}