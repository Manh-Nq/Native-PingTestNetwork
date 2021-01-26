import android.content.Context
import android.graphics.PointF
import android.net.ConnectivityManager
import android.util.Log
import com.github.anastr.speedviewlib.components.Section
import com.tapi.speedtest.MyApp
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.`object`.NetworkTrafficResult
import com.tapi.speedtest.`object`.Vector
import com.tapi.speedtest.database.entity.NetworkTrafficEntity
import com.tapi.speedtest.ui.speedview.view.Gauge
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

typealias OnSpeedChangeListener = (gauge: Gauge, isSpeedUp: Boolean, isByTremble: Boolean) -> Unit

typealias OnSectionChangeListener = (previousSection: Section?, newSection: Section?) -> Unit


typealias OnPrintTickLabelListener = (tickPosition: Int, tick: Float) -> CharSequence?

fun log(text: String) {
    Log.d("TAG", "nmCodeCustomview :  $text ")

}


fun Gauge.doOnSections(action: (section: Section) -> Unit) {
    val sections = ArrayList(this.sections)
    // this will also clear observers.
    this.clearSections()
    sections.forEach { action.invoke(it) }
    this.addSections(sections)
}

fun getRoundAngle(a: Float, d: Float): Float {
    return (a * .5f * 360 / (d  * Math.PI)).toFloat()
}
object Utils {

    val tmp = mutableListOf(
        "gooogle.com", "facebook.com", "youtube.com", "vlxx.com", "1.1.1.1", "8.8.8.8", "8.8.4.4"
    )

    fun listServer(): List<IP> {
        val listIP = mutableListOf<IP>()
        tmp.map {
            listIP.add(IP(it))
        }
        return listIP

    }


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


        if ((is3g != null && is3g) || (isWifi != null && isWifi)) {
            return true
        }
        return false
    }


    fun convertValue(
        min1: Float,
        max1: Float,
        min2: Float,
        max2: Float,
        value: Float
    ): Float {
        return ((value - min1) * ((max2 - min2) / (max1 - min1)) + min2)
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


    fun parseNetworkTrafficEntity(networkTrafficResult: NetworkTrafficResult): NetworkTrafficEntity {
        val networkTrafficEntity = NetworkTrafficEntity()
        networkTrafficEntity.destination = networkTrafficResult.dest
        networkTrafficEntity.host = networkTrafficResult.host
        networkTrafficEntity.duration = networkTrafficResult.duration
        networkTrafficEntity.validateThreshold = System.currentTimeMillis()
        return networkTrafficEntity
    }


    fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt(
            ((x2.toDouble() - x1.toDouble()).pow(2.0) + (x2.toDouble() - x1.toDouble()).pow(2.0))
        ).toFloat()

    }

    fun findYCoordinatis(oldPoint: PointF, newPoint: PointF, xPos: Float): Float {
        //directionVector
//        val directionVector = Vector(p2.x - p1.x, p2.y - p1.y)

        //normalVector
        val normalVector = Vector(x = oldPoint.y - newPoint.y, y = newPoint.x - oldPoint.x)

        return if (((oldPoint.y - newPoint.y).toDouble()
                .pow(2) + (newPoint.x - oldPoint.x).toDouble()
                .pow(2)) > 0
        ) {
            (((normalVector.x * oldPoint.x) + (normalVector.y * oldPoint.y)) - (normalVector.x * xPos)) / normalVector.y
        } else
            -1f
    }

    fun findXCoordinatis(oldPoint: PointF, newPoint: PointF, yPos: Float): Float {
        //directionVector
//        val directionVector = Vector(p2.x - p1.x, p2.y - p1.y)

        //normalVector
        val normalVector = Vector(x = oldPoint.y - newPoint.y, y = newPoint.x - oldPoint.x)

        return if (((oldPoint.y - newPoint.y).toDouble()
                .pow(2) + (newPoint.x - oldPoint.x).toDouble()
                .pow(2)) > 0
        ) {
            (((normalVector.x * oldPoint.x) + (normalVector.y * oldPoint.y)) - (normalVector.y * yPos)) / normalVector.x
        } else {
            -1f
        }

    }
}