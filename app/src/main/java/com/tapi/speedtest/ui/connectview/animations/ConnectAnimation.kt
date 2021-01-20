package com.tapi.speedtest.ui.connectview.animations

import android.animation.ValueAnimator
import com.tapi.speedtest.ui.connectview.ConnectView
import kotlinx.coroutines.delay
import log

class ConnectAnimation(val view: ConnectView) {

    private var animation: ValueAnimator? = null
    private var isCanceled = false


    suspend fun startTextAnim() {
        var text = "CONNECT"
        while (true) {
            delay(500)
            text += "."
            log("${text.lastIndexOf('.')}")
            if (text.lastIndexOf('.') == 10) {
                text = "CONNECT"
            }
            view.setText(text)
            view.updateView()
        }
    }



}