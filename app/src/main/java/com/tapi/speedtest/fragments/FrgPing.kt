package com.tapi.speedtest.fragments

import Utils
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tapi.speedtest.databinding.FragmentFrgPingBinding
import com.tapi.speedtest.manager.vpn.VPNServerChooser
import kotlinx.coroutines.*


class FrgPing : Fragment() {

    var _binding: FragmentFrgPingBinding? = null
    val binding get() = _binding!!
    lateinit var vpnChooser: VPNServerChooser
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFrgPingBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {

        vpnChooser = VPNServerChooser()
        binding.btPing.setOnClickListener {

            showProgress(true)
            myScope.launch {
                val rs = vpnChooser.choose(Utils.listServer())
                withContext(Dispatchers.Main) {
                    showProgress(false)
                    binding.tvResult.text = rs.address
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