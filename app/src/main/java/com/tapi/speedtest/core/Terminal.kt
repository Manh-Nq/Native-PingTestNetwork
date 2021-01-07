package com.tapi.speedtest.core

import android.util.Log
import com.tapi.speedtest.`object`.ICMPReply
import com.tapi.internetprotocoldemo.`object`.ICMPStatistics
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

const val REQUEST_TIME_OUT = "Request timed out"
const val COUNT_REQUEST = 4
const val PING_CMD = "ping -c 1 -w 5 "


class Terminal {

    fun pingCMD(str: String): ICMPReply {
        var icmpReply: ICMPReply
        var result: String
        try {
            var isRequest = false
            var i = 0
            val executeCmd: Process? = executeCmd("$PING_CMD$str", false)
            executeCmd?.let {
                val bufferedReader = BufferedReader(InputStreamReader(executeCmd.inputStream))
                val bufferedReader2 = BufferedReader(InputStreamReader(executeCmd.errorStream))
                val waitFor = executeCmd.waitFor()
                if (waitFor != 0) {
                    if (waitFor != 1) {
                        result = ""
                        while (true) {
                            val readLine = bufferedReader2.readLine() ?: break
                            result = "$result$readLine \n"
                            isRequest = false
                        }
                    } else {
                        icmpReply = ICMPReply(isRequest = false)
                        result = "$REQUEST_TIME_OUT\n"
                    }
                } else {
                    while (true) {
                        val readLine2 = bufferedReader.readLine()
                        if (readLine2 == null) {
                            result = ""
                            break
                        } else if (i == 1) {
                            result = "$readLine2 \n"
                            isRequest = true
                            break
                        } else {
                            i++
                        }
                    }
                }
                executeCmd.destroy()
                Log.d("TAG", "ICMPPackets:result $result")
                icmpReply = if (result.contains("time=")) {
                    parseICMPPackets(result, isRequest)
                } else {
                    ICMPReply(isRequest = false)
                }
                return icmpReply
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ICMPReply()
    }


    private fun parseICMPPackets(str: String, isRequest: Boolean): ICMPReply {
        val icmpPacket = ICMPReply()
        if (str.isNotEmpty())
            icmpPacket.timeToLive = (str.split("ttl=")[1].split(" ")[0])
        icmpPacket.time = (str.split("time=")[1].split(" ")[0])
        icmpPacket.bytes = str.substring(0, str.indexOf(" "))
        icmpPacket.isRequest = isRequest
        return icmpPacket
    }

    fun ping(ip: String): ICMPStatistics {
        var index = 0
        val listICMP = mutableListOf<ICMPReply>()
        while (index < COUNT_REQUEST) {
            listICMP.add(pingCMD(ip))
            index++
        }
        return ICMPStatistics(listICMP, ip)
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

}