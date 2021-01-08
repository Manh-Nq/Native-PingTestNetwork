package com.tapi.speedtest.manager.cmd

import java.math.BigDecimal


/**
 * Speed examples report.
 *
 *
 * feature current report measurement for DOWNLOAD/UPLOAD
 *
 * @author Bertrand Martel
 */
class SpeedTestReport(
    speedTestMode: SpeedTestMode,
    progressPercent: Float,
    startTime: Long,
    reportTime: Long,
    tempPacketSize: Long,
    totalPacketSize: Long,
    transferRateOctet: BigDecimal,
    transferRateBit: BigDecimal,
    requestNum: Int
) {
    /**
     * get current file size.
     *
     * @return packet size in bit
     */
    /**
     * current size of file to upload.
     */
    val temporaryPacketSize: Long
    /**
     * get total file size.
     *
     * @return total file size in bit
     */
    /**
     * total file size.
     */
    val totalPacketSize: Long
    /**
     * get transfer rate in octet/s.
     *
     * @return transfer rate in octet/s
     */
    /**
     * transfer rate in octet/s.
     */
    val transferRateOctet: BigDecimal
    /**
     * get transfer rate in bit/s.
     *
     * @return transfer rate in bit/s
     */
    /**
     * transfer rate in bit/s.
     */
    val transferRateBit: BigDecimal
    /**
     * get speed examples start time.
     *
     * @return start time timestamp (millis since 1970)
     */
    /**
     * upload start time in nanoseconds.
     */
    val startTime: Long
    /**
     * get current timestamp.
     *
     * @return current timestamp for this report measurement (millis since 1970)
     */
    /**
     * upload report time in nanoseconds.
     */
    val reportTime: Long

    /**
     * speed examples mode for this report.
     */
    private val mSpeedTestMode: SpeedTestMode
    /**
     * get speed examples progress.
     *
     * @return progress in %
     */
    /**
     * speed examples progress in percent (%).
     */
    val progressPercent: Float
    /**
     * get request num.
     *
     * @return http request number
     */
    /**
     * number of request.
     */
    val requestNum: Int

    /**
     * get speed examples mode (DOWNLOAD/UPLOAD).
     *
     * @return speed examples mode
     */
    val speedTestMode: SpeedTestMode
        get() = mSpeedTestMode

    /**
     * Build Upload report.
     *
     * @param speedTestMode     speed examples mode (DOWNLOAD/UPLOAD)
     * @param progressPercent   speed examples progress in percent (%)
     * @param startTime         upload start time in nanoseconds
     * @param reportTime        upload report time in nanoseconds
     * @param tempPacketSize    current size of file to upload
     * @param totalPacketSize   total file size
     * @param transferRateOctet transfer rate in octet/s
     * @param transferRateBit   transfer rate in bit/s
     * @param requestNum        number of request for this report
     */
    init {
        mSpeedTestMode = speedTestMode
        this.progressPercent = progressPercent
        this.startTime = startTime
        this.reportTime = reportTime
        temporaryPacketSize = tempPacketSize
        this.totalPacketSize = totalPacketSize
        this.transferRateOctet = transferRateOctet
        this.transferRateBit = transferRateBit
        this.requestNum = requestNum
    }
}