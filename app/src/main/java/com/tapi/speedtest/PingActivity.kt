package com.tapi.speedtest

import Utils
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tapi.speedtest.databinding.ActPingBinding
import com.tapi.speedtest.manager.vpn.VPNServerChooser
import kotlinx.coroutines.*

class PingActivity : AppCompatActivity() {
    private lateinit var binding: ActPingBinding
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    lateinit var vpnChooser: VPNServerChooser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActPingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        vpnChooser = VPNServerChooser()
        binding.btPing.setOnClickListener {
            showProgress(true)
            myScope.launch {
                val rs = vpnChooser.choose(Utils.listServer())
                withContext(Dispatchers.Main) {
                    showProgress(false)
                    Log.d("nmcode", "NManhhh:IP perfect ${rs.address}")
                }
            }
        }

        binding.btGet.setOnClickListener {
            myScope.launch {
                val rs = vpnChooser.deleteAll()
//                Log.d("nmcode", "NManhhh: $rs")
            }

        }
    }

    private fun showProgress(b: Boolean) {
        if (b) {
            binding.prg.visibility = View.VISIBLE
        } else {
            binding.prg.visibility = View.INVISIBLE
        }
    }


}