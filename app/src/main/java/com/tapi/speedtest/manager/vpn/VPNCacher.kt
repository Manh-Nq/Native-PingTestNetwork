package com.tapi.speedtest.manager.vpn

import com.tapi.speedtest.MyApp
import com.tapi.speedtest.database.entity.NetworkTrafficEntity

class VPNCacher {

    suspend fun saveResult(result: NetworkTrafficEntity) {
        MyApp.terminalDB.terminalDAO.saveResult(result)
    }


    suspend fun getIPOrNull(host: String, dest: String): NetworkTrafficEntity? {
        val networkItem =
            MyApp.terminalDB.terminalDAO.getOrNull(host, dest, System.currentTimeMillis())
        if (networkItem != null) {
            return networkItem
        }
        return null
    }


    suspend fun getAllTable(): List<NetworkTrafficEntity> {
        return MyApp.terminalDB.terminalDAO.getAllICMP()
    }

    suspend fun deleteAll() {
        return MyApp.terminalDB.terminalDAO.deleteAllTable()
    }


}