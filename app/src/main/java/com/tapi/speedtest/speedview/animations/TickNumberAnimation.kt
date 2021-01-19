package com.tapi.speedtest.speedview.animations

import com.tapi.speedtest.speedview.view.Speedometer
import com.tapi.speedtest.speedview.view.SpeedometerView
import kotlinx.coroutines.delay

class TickNumberAnimation(val arcView: SpeedometerView, val speedometer: Speedometer) {
    suspend fun start() {
//        isCanceled = false
        speedometer.endTickPosition = -1
        arcView.invalidateGauge()

        for (index in 0..speedometer.tickNumber) {
            delay(100)
            speedometer.endTickPosition = index
            arcView.setRatioAlpha(((index.toFloat() / speedometer.tickNumber) * 255).toInt())
            arcView.invalidateGauge()
        }
    }

    fun resetView() {
        speedometer.endTickPosition = -1
        arcView.invalidateGauge()
    }


}