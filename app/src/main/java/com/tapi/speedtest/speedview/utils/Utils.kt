package com.tapi.speedtest.speedview.utils

import com.tapi.speedtest.speedview.view.Gauge
import com.github.anastr.speedviewlib.components.Section



typealias OnSpeedChangeListener = (gauge: Gauge, isSpeedUp: Boolean, isByTremble: Boolean) -> Unit

typealias OnSectionChangeListener = (previousSection :Section?, newSection : Section?) -> Unit


typealias OnPrintTickLabelListener = (tickPosition :Int, tick :Float) -> CharSequence?



fun Gauge.doOnSections(action: (section: Section) -> Unit) {
    val sections = ArrayList(this.sections)
    // this will also clear observers.
    this.clearSections()
    sections.forEach { action.invoke(it) }
    this.addSections(sections)
}

fun getRoundAngle(a: Float, d: Float): Float {
    return (a * .5f * 360 / (d  * Math.PI)).toFloat()
}