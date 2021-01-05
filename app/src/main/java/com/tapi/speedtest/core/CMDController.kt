package com.tapi.speedtest.core

import android.util.Log
import com.tapi.speedtest.`object`.ICMPPackets
import com.tapi.speedtest.`object`.ICMPRequest
import java.io.BufferedReader
import java.io.InputStreamReader


class CMDController {

    var host: String = ""
    var resultPing: String = ""
    var success = 0
    var failed = 0
    var pingError = false
    var steps = 0
    var timeList: ArrayList<Double> = ArrayList()
    var ratio: Int = 0
    var onlyRequest: String = ""

    private fun pingCMD(str: String): ICMPRequest? {
        var result: String
        return try {
            this.host = str
            var i = 0
            val executeCmd: Process? = executeCmd("ping -c 1 -w 5 $str", false)
            val bufferedReader = BufferedReader(InputStreamReader(executeCmd?.inputStream))
            val bufferedReader2 = BufferedReader(InputStreamReader(executeCmd?.errorStream))
            val waitFor = executeCmd?.waitFor()
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
            executeCmd?.destroy()
            if (result.contains("time=")) {
                val time = (result.split("time=")[1].split(" ")[0]).toDouble()
                timeList.add(time)
            }
            Log.d("TAG", "pingCMD: $result")
            getICMPRequest(result)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun runCMDPingHost(ip: String, count: Int): ICMPPackets {
        val listICMPRequest = mutableListOf<ICMPRequest>()
        var index = 0
        while (index < count) {
            val item = pingCMD(ip)
            item?.let {
                listICMPRequest.add(it)
            }
            index++
        }
        if (failed != 0) {
            ratio = failed * 100 / steps
        }
        val icmpPackets = ICMPPackets(
            listICMPRequest, calculateMinTime(),
            calculateMaxTime(), calculateAverage(), steps, success, failed, ratio
        )
        /*stopPingHost()*/
        return icmpPackets

    }

    private fun getICMPRequest(str: String): ICMPRequest {
        val icmpPacket = ICMPRequest()
        if (str.isNotEmpty())
            icmpPacket.hostName = this.host
        icmpPacket.timeToLife = (str.split("ttl=")[1].split(" ")[0])
        icmpPacket.time = (str.split("time=")[1].split(" ")[0])
        icmpPacket.bytes = str.substring(0, str.indexOf(" "))
        icmpPacket.result = str
        onlyRequest += str
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

        if (failed != 0) {
            ratio = failed * 100 / steps
        }
        return if (timeList.size > 0) {
            resultPing =
                "\n$onlyRequest \nPing statistics for " + host +
                        ": Packets: Sent = " +
                        steps + " , Received = " +
                        success + " Lost = " + failed +
                        " (" + ratio + "% loss), " +
                        " Approximate round trip times in milli-seconds:     Minimum = " + calculateMinTime() +
                        " ms, Maximum = " + calculateMaxTime() + "ms , Average =" + calculateAverage() + "ms"
            Log.d("TAG", "stopPingHost: $resultPing onlyRequest $onlyRequest")
            resultPing

        } else {
            "No requests"
        }

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
//        java.lang.Double.isNaN(size)
            return (d / size).toInt()
        }
        return -1
    }

    fun resetParameters() {
        host = ""
        resultPing = ""
        success = 0
        failed = 0
        pingError = false
        steps = 0
        timeList.clear()
        ratio = 0
        onlyRequest = ""
    }
}