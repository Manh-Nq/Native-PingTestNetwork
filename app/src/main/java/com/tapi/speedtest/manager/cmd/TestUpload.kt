package com.tapi.speedtest.manager.cmd

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class TestUpload {
    private var mStartDateRepeat: Long = 0

    /**
     * transfer rate list.
     */
    private var mRepeatTransferRateList = Collections.synchronizedList(ArrayList<BigDecimal>())

    /**
     * define if download repeat task is finished.
     */
    private var mRepeatFinished = false

    /**
     * number of packet downloaded for download/upload repeat task.
     */
    private var mRepeatTempPckSize: Long = 0

    /**
     * define if upload should be repeated.
     */
    private var mRepeatUpload = false

    /**
     * time window for download repeat task.
     */
    private var mRepeatWindows = 0

    /**
     * current number of request for download repeat task.
     */
    private var mRepeatRequestNum = 0

    /**
     * define if download should be repeated.
     */
    private var mRepeatDownload = false

    /**
     * number of packet pending for download repeat task.
     */
    private var mRepeatPacketSize = BigDecimal.ZERO

    /**
     * define if the first download repeat has been sent and waiting for connection
     * It is reset to false when the client is connected to server the first time.
     */
    private var mFirstDownloadRepeat = false

    /**
     * define if the first upload repeat has been sent and waiting for connection
     * It is reset to false when the client is connected to server the first time.
     */
    private var mFirstUploadRepeat = false


    private fun initRepeatVars() {
        mRepeatRequestNum = 0
        mRepeatPacketSize = BigDecimal.ZERO
        mRepeatTempPckSize = 0
        mRepeatFinished = false
        mStartDateRepeat = 0
        mRepeatTransferRateList = ArrayList()
    }

    fun getRepeatReport(
        scale: Int,
        roundingMode: RoundingMode,
        speedTestMode: SpeedTestMode,
        reportTime: Long,
        transferRateOctet: BigDecimal
    ): SpeedTestReport {
        var progressPercent = BigDecimal.ZERO
        var temporaryPacketSize: Long = 0
        var downloadRepeatRateOctet = transferRateOctet
        var downloadRepeatReportTime = reportTime
        progressPercent = if (mStartDateRepeat != 0L) {
            if (!mRepeatFinished) {
                val test: Long = System.nanoTime() - mStartDateRepeat
                BigDecimal(test).multiply(SpeedTestConst.PERCENT_MAX)
                    .divide(
                        BigDecimal(mRepeatWindows).multiply(BigDecimal(1000000)),
                        scale,
                        roundingMode
                    )
            } else {
                SpeedTestConst.PERCENT_MAX
            }
        } else {
//download has not started yet
            BigDecimal.ZERO
        }
        var rates = BigDecimal.ZERO
        for (rate in mRepeatTransferRateList) {
            rates = rates.add(rate)
        }
        if (mRepeatTransferRateList.isNotEmpty()) {
            downloadRepeatRateOctet = rates.add(downloadRepeatRateOctet).divide(
                BigDecimal(
                    mRepeatTransferRateList
                        .size
                ).add(
                    BigDecimal(mRepeatTempPckSize).divide(mRepeatPacketSize, scale, roundingMode)
                ), scale, roundingMode
            )
        }
        val transferRateBit = downloadRepeatRateOctet.multiply(SpeedTestConst.BIT_MULTIPLIER)
        if (!mRepeatFinished) {
            temporaryPacketSize = mRepeatTempPckSize
        } else {
            temporaryPacketSize = mRepeatTempPckSize
            downloadRepeatReportTime = BigDecimal(mStartDateRepeat).add(
                BigDecimal(mRepeatWindows).multiply(
                    BigDecimal(1000000)
                )
            ).toLong()
        }
        return SpeedTestReport(
            speedTestMode,
            progressPercent.toFloat(),
            mStartDateRepeat,
            downloadRepeatReportTime,
            temporaryPacketSize,
            mRepeatPacketSize.longValueExact(),
            downloadRepeatRateOctet,
            transferRateBit,
            mRepeatRequestNum
        )
    }


}