package com.tapi.speedtest.fragments

import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tapi.speedtest.databinding.FragmentFrgSpeedLinevBinding
import kotlinx.coroutines.*


class FrgSpeedLinev : Fragment() {

    private var rd = 0
    var _binding: FragmentFrgSpeedLinevBinding? = null
    val binding get() = _binding!!
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFrgSpeedLinevBinding.inflate(inflater, container, false)
        binding.lineview.post {
            initViews()
        }
        return binding.root
    }

    private fun initViews() {

        myScope.launch {
            binding.lineview.post {
                binding.lineview.init()
            }

            for (item in 1..1000) {
                rd = (0..100).random()
                delay(2)
                withContext(Dispatchers.Main) {
                    Log.d("TAG", "initViews: rando, $rd")
                    val point = PointF(item.toFloat(), rd.toFloat())
                    binding.lineview.startDrawDownload(point, 10, item.toFloat())
                }
            }
            binding.lineview.setColorPathDownload()

        }
    }

}