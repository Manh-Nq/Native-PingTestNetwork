package com.tapi.speedtest


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.core.Terminal
import com.tapi.speedtest.databinding.ActivityMainBinding
import com.tapi.speedtest.manager.speedtest.NetworkEvent
import com.tapi.speedtest.manager.vpn.VPNServerChooser
import com.tapi.speedtest.util.Utils
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.model.SpeedTestError
import kotlinx.coroutines.*
import java.math.RoundingMode

class MainActivity : AppCompatActivity(), NetworkEvent.NetworkListener {

    lateinit var spt: SpeedTestSocket
    lateinit var listUpload: MutableList<Double>
    lateinit var networkEvent: NetworkEvent
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listUpload = mutableListOf()

        spt = SpeedTestSocket()
        networkEvent = NetworkEvent(this)

        spt.defaultRoundingMode = RoundingMode.HALF_EVEN
        spt.addSpeedTestListener(networkEvent)


        binding.btDownload.setOnClickListener {
            myScope.launch {
                spt.startDownload(Constance.URI_SPEED_TEST_DOWNLOAD_1M)

            }
        }

        binding.btUpload.setOnClickListener {
            showProgress(true)
            myScope.launch {
/*  val rs = Utils.getIPAddress(true)
                Log.d("nmcode", "NManhhh: $rs")*/
                spt.startUpload(Constance.URI_SPEED_TEST_UPLOAD, 1000000)

            }
        }


    }

    private fun showProgress(b: Boolean) {
        if (b) {
            binding.prg.visibility = View.VISIBLE
        } else {
            binding.prg.visibility = View.INVISIBLE
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(report: SpeedTestReport) {
        super.onSuccess(report)
        MainScope().launch {
            showProgress(false)
        }
        Log.e("nmcode", "[SUCCESS]  RateBit: ${report.transferRateBit} octet/s")
        Log.e(
            "nmcode",
            "[SUCCESS]  RateOctet: ${report.transferRateOctet} bit/s"
        )
    }

    override fun onFail(speedTestError: SpeedTestError?, errorMessage: String) {
        super.onFail(speedTestError, errorMessage)
    }

    override fun onRunning(percent: Float, report: SpeedTestReport) {
        super.onRunning(percent, report)
        binding.sbPercent.progress = percent.toInt()
        Log.d("nmcode", "[RUNNING]  percent: ${percent}%")
        Log.d("nmcode", "[RUNNING]  RateBit: ${report.transferRateBit} bit/s")
        Log.d("nmcode", "[RUNNING]  RateOctet: ${report.transferRateOctet} ocTet/s")
    }

}