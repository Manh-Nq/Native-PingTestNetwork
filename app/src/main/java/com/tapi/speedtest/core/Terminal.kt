package com.tapi.speedtest.core

import android.util.Log
import com.tapi.internetprotocoldemo.`object`.ICMPStatistics
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.`object`.ICMPReply
import java.io.BufferedReader
import java.io.InputStreamReader



class Terminal {

 private fun pingCMD(str: String): ICMPReply {
        var icmpReply: ICMPReply
        var result: String
        try {
            var isRequest = false
            var i = 0
            val executeCmd: Process? = executeCmd("${Constance.PING_CMD}$str", false)
            executeCmd?.let {
                val bufferedReaderSuccess =
                    BufferedReader(InputStreamReader(executeCmd.inputStream))
                val bufferedReaderErr = BufferedReader(InputStreamReader(executeCmd.errorStream))
                val waitFor = executeCmd.waitFor()
                if (waitFor != 0) {
                    if (waitFor != 1) {
                        result = ""
                        while (true) {
                            val readLine = bufferedReaderErr.readLine() ?: break
                            result = "$result$readLine \n"
                            isRequest = false
                        }
                    } else {
                        icmpReply = ICMPReply(isRequest = false)
                        result = "${Constance.REQUEST_TIME_OUT}\n"
                    }
                } else {
                    while (true) {
                        val readLine2 = bufferedReaderSuccess.readLine()
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
                Log.d("TAG", "NManhhh: $result")
                icmpReply = if (result.contains("time=")) {
                    parseICMPPackets(result, isRequest)
                } else {
                    ICMPReply(ttl = Constance.TTL_DEFAULT, isRequest = false)
                }
                return icmpReply
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ICMPReply()
    }


    private fun parseICMPPackets(str: String, isRequest: Boolean): ICMPReply {
        val icmpReply = ICMPReply()
        if (str.isNotEmpty())
            icmpReply.ttl = (str.split("ttl=")[1].split(" ")[0])
        icmpReply.time = (str.split("time=")[1].split(" ")[0])
        icmpReply.bytes = str.substring(0, str.indexOf(" "))
        icmpReply.isRequest = isRequest
        return icmpReply
    }

    fun  ping(ip: String): ICMPStatistics {
        var index = 0
        val listICMP = mutableListOf<ICMPReply>()
        while (index < Constance.COUNT_REQUEST) {
            listICMP.add(pingCMD(ip))
            index++
        }
        val item = ICMPStatistics(listICMP, ip, System.currentTimeMillis())
        Log.d("TAG", "NManhhh: ${item.calculateAverage()} - host ${item.destination}")
        return ICMPStatistics(listICMP, ip, System.currentTimeMillis())
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