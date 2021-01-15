package com.github.anastr.speedviewlib.components.indicators

import android.content.Context
import android.graphics.Canvas
import com.tapi.speedtest.speedview.components.indicators.Indicator

/**
 * this Library build By Anas Altair
 * see it on [GitHub](https://github.com/anastr/SpeedView)
 */
class NoIndicator(context: Context) : Indicator<NoIndicator>(context) {

    override fun draw(resources: Context, canvas: Canvas, degree: Float) {}

    override fun updateIndicator() {}

    override fun setWithEffects(withEffects: Boolean) {}
}