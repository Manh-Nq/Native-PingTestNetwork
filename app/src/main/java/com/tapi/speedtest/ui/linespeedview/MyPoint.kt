package com.tapi.speedtest.ui.linespeedview

data class MyPoint(val x: Float, val y: Float) {
    override fun toString(): String {
        return "speed: $x  - percent: $y"
    }
}
