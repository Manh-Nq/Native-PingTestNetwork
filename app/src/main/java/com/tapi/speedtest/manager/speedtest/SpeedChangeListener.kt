package com.tapi.speedtest.manager.speedtest

internal interface SpeedChangeListener {
    fun onSpeedChanged(newSpeedValue: Float)
}