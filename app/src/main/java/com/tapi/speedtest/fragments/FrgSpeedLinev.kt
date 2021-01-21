package com.tapi.speedtest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tapi.speedtest.databinding.FragmentFrgSpeedLinevBinding
import kotlinx.coroutines.*
import java.util.*


class FrgSpeedLinev : Fragment() {

    var _binding: FragmentFrgSpeedLinevBinding? = null
    val binding get() = _binding!!
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFrgSpeedLinevBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
      /*  myScope.launch {
            runLine()
        }*/
    }

    private suspend fun runLine() {
        val yPos = Random(100).nextFloat()
        for (i in 0..1000) {
            delay(50)
            withContext(Dispatchers.Main) {
                binding.lineview.setPos(i.toFloat(), yPos + i)
            }
        }
    }

}