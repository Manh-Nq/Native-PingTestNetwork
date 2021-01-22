package com.tapi.speedtest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.tapi.speedtest.databinding.FragmentFrgSpeedLinevBinding
import kotlinx.coroutines.*


class FrgSpeedLinev : Fragment() {

    private var series: LineGraphSeries<DataPoint>? = null
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
            /*Constance.LIST_ANGLE.forEach {
                delay(200)
                withContext(Dispatchers.Main) {
                    binding.lineview.startDraw(it.x, it.y)
                }
            }*/
           /* for (item in 0..100) {
                delay(200)
                withContext(Dispatchers.Main) {
                    binding.lineview.startDraw(item.toFloat())
                }
            }*/
        }
    }

}