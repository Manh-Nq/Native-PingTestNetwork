package com.tapi.speedtest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tapi.speedtest.R
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.databinding.FragmentUploadFrgBinding
import com.tapi.speedtest.manager.speedtest.NetworkEvent
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.model.SpeedTestError
import kotlinx.coroutines.*
import java.math.RoundingMode


class UploadFrg : Fragment(), NetworkEvent.NetworkListener {

    var _binding: FragmentUploadFrgBinding? = null
    val binding get() = _binding!!

    lateinit var spt: SpeedTestSocket
    lateinit var networkEvent: NetworkEvent
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUploadFrgBinding.inflate(inflater, container, false)
        initViews()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initViews() {
        spt = SpeedTestSocket()
        networkEvent = NetworkEvent(this)

        spt.defaultRoundingMode = RoundingMode.HALF_EVEN
        spt.addSpeedTestListener(networkEvent)


        binding.btDownload.setOnClickListener {
            showProgress(true)
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

    @SuppressLint("SetTextI18n")
    override fun onSuccess(report: SpeedTestReport) {
        super.onSuccess(report)
        MainScope().launch {
            binding.tvResult.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorRed))
            binding.tvResult.text =
                " ${report.transferRateBit} octet/s \n  ${report.transferRateOctet} bit/s"
            showProgress(false)
        }

        Log.e("nmcode", "[SUCCESS]  RateBit: ${report.transferRateBit} octet/s")
        Log.e("nmcode", "[SUCCESS]  RateOctet: ${report.transferRateOctet} bit/s")
    }

    override fun onFail(speedTestError: SpeedTestError?, errorMessage: String) {
        super.onFail(speedTestError, errorMessage)
    }

    override fun onRunning(percent: Float, report: SpeedTestReport) {
        super.onRunning(percent, report)
        binding.sbPercent.progress = percent.toInt()
        Log.d("nmcode", "[RUNNING]  percent: ${percent}%")

        /*       Log.d("nmcode", "[RUNNING]  RateOctet: ${report.transferRateOctet} L")
               Log.d("nmcode", "[RUNNING]  reportTime: ${report.reportTime}Long")
               Log.d("nmcode", "[RUNNING]  requestNum: ${report.requestNum} ")
               Log.d("nmcode", "[RUNNING]  totalPacketSize: ${report.totalPacketSize} L")
               Log.d("nmcode", "[RUNNING]  temporaryPacketSize: ${report.temporaryPacketSize} L")
               Log.d("nmcode", "[RUNNING]  startTime: ${report.startTime} L")*/
    }


}