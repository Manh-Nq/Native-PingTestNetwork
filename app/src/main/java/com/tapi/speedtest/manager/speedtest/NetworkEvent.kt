package com.tapi.speedtest.manager.speedtest

import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.inter.ISpeedTestListener
import fr.bmartel.speedtest.model.SpeedTestError

class NetworkEvent(private val mCallback: NetworkListener) : ISpeedTestListener {


    override fun onCompletion(report: SpeedTestReport) {
        mCallback.onSuccess(report)
    }

    override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
        mCallback.onFail(speedTestError, errorMessage)
    }

    override fun onProgress(percent: Float, report: SpeedTestReport) {
        mCallback.onRunning(percent, report)
    }

    interface NetworkListener {
        fun onSuccess(report: SpeedTestReport) {

        }

        fun onFail(speedTestError: SpeedTestError?, errorMessage: String) {

        }

        fun onRunning(percent: Float, report: SpeedTestReport) {

        }
    }
}