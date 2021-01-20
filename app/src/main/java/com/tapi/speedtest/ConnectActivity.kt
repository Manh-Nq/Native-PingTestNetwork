package com.tapi.speedtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tapi.speedtest.ui.connectview.ConnectView

class ConnectActivity : AppCompatActivity() {
    private lateinit var pgview: ConnectView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_view)
        pgview = findViewById(R.id.pg_view)
        lifecycleScope.launchWhenResumed {
            pgview.startAnim()

        }
    }
}