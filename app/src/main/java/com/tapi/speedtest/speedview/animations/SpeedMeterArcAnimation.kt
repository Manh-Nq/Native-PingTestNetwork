package com.tapi.speedtest.speedview.animations

import android.animation.ValueAnimator
import com.tapi.speedtest.speedview.view.SpeedometerView

class SpeedMeterArcAnimation(val arcView: SpeedometerView) {
    private var animation: ValueAnimator? = null
    private var isCanceled = false

    fun cancel() {
        isCanceled = true
    }

    fun start(fromAngle: Float, toAngle: Float) {
        isCanceled = false
        animation?.cancel()
        animation = ValueAnimator.ofFloat(fromAngle, toAngle)
        animation?.apply {
            duration = 1000
            addUpdateListener {
                if (!isCanceled) {
                    val currValue = it.animatedValue as Float
                    arcView.setSweepAngle(currValue)
                    arcView.invalidate()
                }
            }
            start()
        }
    }
}