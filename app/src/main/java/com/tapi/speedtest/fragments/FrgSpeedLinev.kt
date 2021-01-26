package com.tapi.speedtest.fragments

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.databinding.FragmentFrgSpeedLinevBinding
import kotlinx.coroutines.*


class FrgSpeedLinev : Fragment() {

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
                  delay(5)
                  withContext(Dispatchers.Main) {
                      val rd = (1..100).random()
                      val point = PointF(item.toFloat(), rd.toFloat())
                      binding.lineview.startDraw(point, 10)
                  }
              }

        }
    }

}