package com.tapi.speedtest.util

import android.content.Context
import android.net.ConnectivityManager
import com.tapi.speedtest.MyApp
import com.tapi.speedtest.`object`.IP
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*


object Utils {


    fun convertIP(ip: String): IP {
        return IP(ip)
    }

    fun isNetworkAvailable(): Boolean {
        val manager = MyApp.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //For 3G check
        val is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnectedOrConnecting
        //For WiFi Check
        val isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnectedOrConnecting


        if (!is3g!! && !isWifi!!) {
            return false
        }
        return true
    }


    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val sAddr = addr.hostAddress
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        val isIPv4 = sAddr.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                return if (delim < 0) sAddr.toUpperCase(Locale.ROOT) else sAddr.substring(
                                    0,
                                    delim
                                ).toUpperCase(Locale.ROOT)
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        } // for now eat exceptions
        return ""
    }

}