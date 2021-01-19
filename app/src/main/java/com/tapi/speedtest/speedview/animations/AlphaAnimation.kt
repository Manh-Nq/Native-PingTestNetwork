package com.tapi.speedtest.speedview.animations

import com.tapi.speedtest.speedview.view.SpeedometerView
import kotlinx.coroutines.delay

class AlphaAnimation(val arcView: SpeedometerView) {

    var isCanceled = false

    fun cancel() {
        isCanceled = true
    }

    suspend fun start() {

        var isCanceled = false
        arcView.setRatioAlpha(0)
        arcView.invalidateGauge()
        for (index in 0..255) {
            delay(5)
            arcView.setRatioAlpha(index)
            arcView.invalidateGauge()
        }
    }


}