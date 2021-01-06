package com.tapi.speedtest.core

import android.util.Log
import com.tapi.speedtest.`object`.ICMPPackets
import com.tapi.internetprotocoldemo.`object`.ICMPStatistics
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

const val REQUEST_TIME_OUT = "Request timed out"
const val COUNT_REQUEST = 4
const val PING_CMD = "ping -c 1 -w 5 "


class Terminal {


    private val sort: Comparator<ICMPStatistics> = object : Comparator<ICMPStatistics> {
        override fun compare(item1: ICMPStatistics, item2: ICMPStatistics): Int {
            if (item1.average == -1 || item2.average == -1) return 0
            if (item1.average == item2.average) {
                return item1.ratioLost - item2.ratioLost
            }
            return item1.average - item2.average
        }
    }


    var host: String = ""
    var resultPing: String = ""
    var onlyPing: String = ""
    var pingError = false
    var timeList: ArrayList<Double> = ArrayList()
    var steps = 0
    var ratio: Int = 0
    var success = 0
    var failed = 0
    var timeToLive = 0


    private fun pingCMD(str: String): ICMPPackets? {
        var result: String
        return try {
            this.host = str
            var i = 0
            val executeCmd: Process? = executeCmd("$PING_CMD$str", false)
            executeCmd?.let {
                val bufferedReader = BufferedReader(InputStreamReader(executeCmd.inputStream))
                val bufferedReader2 = BufferedReader(InputStreamReader(executeCmd.errorStream))
                val waitFor = executeCmd.waitFor()
                this.pingError = false
                this.steps++
                if (waitFor != 0) {
                    if (waitFor != 1) {
                        this.pingError = true
                        result = ""
                        while (true) {
                            val readLine = bufferedReader2.readLine() ?: break
                            result = "$result$readLine \n"
                            this.failed++
                        }
                    } else {
                        result = "Request timed out\n"
                        this.failed++
                    }
                } else {
                    while (true) {
                        val readLine2 = bufferedReader.readLine()
                        if (readLine2 == null) {
                            result = ""
                            break
                        } else if (i == 1) {
                            result = "$readLine2 \n"
                            break
                        } else {
                            i++
                            this.success++
                        }
                    }
                }
                executeCmd.destroy()
                onlyPing += result
                if (result.contains("time=")) {
                    val time = (result.split("time=")[1].split(" ")[0]).toDouble()
                    timeList.add(time)
                    val itemICMP = parseICMPPackets(result)
                    timeToLive = itemICMP.timeToLive.toInt()
                    itemICMP
                } else {
                    if (timeToLive != 0) {
                        timeList.add(timeToLive.toDouble())
                    }
                    null
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            stopPingHost()
        }
    }

    fun ping(ip: String): ICMPStatistics {
        val listICMPRequest = mutableListOf<ICMPPackets>()
        var index = 0
        while (index < COUNT_REQUEST) {
            val item = pingCMD(ip)
            item?.let {
                listICMPRequest.add(it)
            }
            index++
        }
        if (failed != 0) {
            ratio = failed * 100 / steps
        }
        val rs = stopPingHost()
        val icmpStatistics = ICMPStatistics(
            listICMPRequest, calculateMinTime(),
            calculateMaxTime(), calculateAverage(), steps, success, failed, ratio, rs, host
        )

        Log.d("TAG", "ICMPPackets: $rs")
        if (index == COUNT_REQUEST) {
            resetParameters()
        }
        return icmpStatistics

    }

    private fun parseICMPPackets(str: String): ICMPPackets {
        val icmpPacket = ICMPPackets()
        if (str.isNotEmpty())
            icmpPacket.hostName = this.host
        icmpPacket.timeToLive = (str.split("ttl=")[1].split(" ")[0])
        icmpPacket.time = (str.split("time=")[1].split(" ")[0])
        icmpPacket.bytes = str.substring(0, str.indexOf(" "))
        return icmpPacket

    }


    private fun executeCmd(str: String, rs: Boolean): Process? {
        return if (!rs) {
            try {
                Runtime.getRuntime().exec(str)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            Runtime.getRuntime().exec(arrayOf("su", "-c", str))
        }
    }

    private fun stopPingHost(): String {
        if (timeList.size > 0) {
            resultPing =
                " \n $onlyPing \nPing statistics for " + host +
                        ": Packets: Sent = " +
                        steps + " , Received = " +
                        success + " Lost = " + failed +
                        " (" + ratio + "% loss), " +
                        " Approximate round trip times in milli-seconds:     Minimum = " + calculateMinTime() +
                        " ms, Maximum = " + calculateMaxTime() + "ms , Average =" + calculateAverage() + "ms"
            resultPing

        } else {
            resultPing = "No requests"
        }
        return resultPing

    }


    private fun calculateMaxTime(): Double {
        if (timeList.isNotEmpty()) {
            var d = timeList[0]
            for (i in timeList.indices) {
                if (timeList[i] > d) {
                    d = timeList[i]
                }
            }
            return d
        }
        return 0.0
    }

    private fun calculateMinTime(): Double {
        if (timeList.isNotEmpty()) {
            var d = timeList[0]
            for (i in timeList.indices) {
                if (timeList[i] < d) {
                    d = timeList[i]
                }
            }
            return d
        }
        return 0.0

    }

    private fun calculateAverage(): Int {
        if (timeList.isNotEmpty()) {
            var d = 0.0
            for (i in timeList) {
                d += i
            }
            val size = timeList.size.toDouble()
            return (d / size).toInt()
        }
        return -1
    }


    private fun resetParameters() {
        host = ""
        resultPing = ""
        pingError = false
        onlyPing = ""
        timeList.clear()
        ratio = 0
        steps = 0
        success = 0
        failed = 0
    }
}