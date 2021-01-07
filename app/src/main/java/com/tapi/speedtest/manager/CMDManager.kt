package com.tapi.internetprotocoldemo.manager


import com.tapi.internetprotocoldemo.`object`.ICMPStatistics
import com.tapi.speedtest.MyApp
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.core.Terminal
import com.tapi.speedtest.database.entity.ICMPEntity
import java.util.*

class CMDManager {
    companion object {
        val terminal = Terminal()

    }

    private val sortIP: Comparator<ICMPStatistics> = object : Comparator<ICMPStatistics> {
        override fun compare(item1: ICMPStatistics, item2: ICMPStatistics): Int {
            if (item1.calculateAverage() == -1 || item2.calculateAverage() == -1) return 0
            if (item1.calculateAverage() == item2.calculateAverage()) {
                return item1.filterRatio() - item2.filterRatio()
            }
            return item1.calculateAverage() - item2.calculateAverage()
        }
    }

    fun filterIPPerfect(listIP: List<String>): String {
        val listICMP = mutableListOf<ICMPStatistics>()

        listIP.map {
            listICMP.add(terminal.ping(it))
        }
        Collections.sort(listICMP, sortIP)
        return listICMP[0].destination

    }

    suspend fun pingAllList(listIP: List<String>): String {
        val listICMP = MyApp.terminalDB.terminalDAO.getAllICMP()
        var ipPerfect = listICMP[0]

        listICMP.forEach {
            if (it.timeAverage < ipPerfect.timeAverage) {
                ipPerfect = it
            }
        }
        if (listIP.contains(ipPerfect.destination) && (System.currentTimeMillis() - ipPerfect.timeScanned) <= Constance.TIME_CONFIG) {
            return ipPerfect.destination
        } else {
            scanAndFilterList(listIP)
        }
        return ""
    }

    private suspend fun scanAndFilterList(listIP: List<String>) {
        val rs = scanPingIP(listIP)
        if (rs) {
            pingAllList(listIP)
        }
    }

    suspend fun scanPingIP(listIP: List<String>): Boolean {
        try {
            listIP.map {
                val itemICMP = terminal.ping(it)
                val icmpEntity = ICMPEntity()
                icmpEntity.destination = itemICMP.destination
                icmpEntity.host = "1.1.1.1"
                icmpEntity.timeAverage = itemICMP.calculateAverage()
                icmpEntity.timeScanned = itemICMP.currentTime
                MyApp.terminalDB.terminalDAO.saveICMP(icmpEntity)
            }
            return true

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}