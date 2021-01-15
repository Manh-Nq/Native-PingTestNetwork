package com.tapi.speedtest.speedview.anim

import android.view.animation.Animation
import android.view.animation.Transformation
import com.tapi.speedtest.speedview.view.SpeedometerView

class ArcAngleAnimation(val arcView: SpeedometerView, val newAngle: Float) : Animation() {
    val oldAngle: Float = arcView.getArcAngle()
    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation?) {
        val angle = 0 + (newAngle - oldAngle) * interpolatedTime
        arcView.setArcAngle(angle)
        arcView.requestLayout()
    }


}