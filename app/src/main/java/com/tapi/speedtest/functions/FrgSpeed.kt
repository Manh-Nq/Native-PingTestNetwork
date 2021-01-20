package com.tapi.speedtest.functions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tapi.speedtest.R
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.databinding.FragmentFrgSpeedBinding
import kotlinx.coroutines.*
import kotlin.random.Random

class FrgSpeed : Fragment() {
    private var a: Float = 0f
    private var _binding: FragmentFrgSpeedBinding? = null
    val binding get() = _binding!!
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var isChange = true
    private var isShow = true
    private var maxSp = 100f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFrgSpeedBinding.inflate(layoutInflater)
        showSpeedDometers()
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.spRate.withTremble = false
        binding.btChange100.setOnClickListener {
            if (!isChange) {
                maxSp = 1000f
                binding.spRate.maxSpeed = maxSp
                binding.spRate.unit = "Mb/s"
                binding.spRate.speedPercentTo(a.toInt(), 300)
                isChange = !isChange
            }

        }
        binding.btChange500.setOnClickListener {
            if (isChange) {
                maxSp = 500f
                binding.spRate.maxSpeed = maxSp
                binding.spRate.unit = "Kb/s"
                binding.spRate.speedPercentTo((a).toInt(), 300)
                isChange = !isChange
            }
        }


        binding.btTest.setOnClickListener {
            if (isShow) {
                binding.spRate.visibility = View.VISIBLE
                lifecycleScope.launchWhenResumed {
                    binding.spRate.start()
                }
            } else {
                binding.spRate.setRatioAlpha(0)
                binding.spRate.speedTo(0f, 0)
                binding.spRate.withTremble = false
                binding.spRate.visibility = View.GONE
            }
            isShow = !isShow

        }

        binding.btSpeed.setOnClickListener {
            myScope.launch {
                a = (Random.nextInt(61) + 20f)
                withContext(Dispatchers.Main) {
                    if (binding.spRate.ratioAlpha == Constance.MAX_ALPHA) {
                        Log.d("TAG", "initViews: $a")
                        binding.spRate.speedTo(a)
                    } else {
                        Log.d("TAG", "initViews: you can't set speed")
                    }

                }
            }
        }
        binding.btNext.setOnClickListener {
            findNavController().navigate(R.id.action_frgSpeed_to_frgNext)
        }

    }

    private fun showSpeedDometers() {
        binding.spRate.visibility = View.VISIBLE
        lifecycleScope.launchWhenResumed {
            if (maxSp != 100f) {
                binding.spRate.maxSpeed = maxSp
            }
            binding.spRate.startFlash()
            if (a != 0f) {
                binding.spRate.speedPercentTo(a.toInt(), 0)
            }

        }
    }
}