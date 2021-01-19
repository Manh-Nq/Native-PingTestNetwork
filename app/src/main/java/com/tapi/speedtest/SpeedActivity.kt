package com.tapi.speedtest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.databinding.SpeedParameterActBinding
import kotlinx.coroutines.*
import kotlin.random.Random

class SpeedActivity : AppCompatActivity() {
    private var a: Float = 0f
    private var _binding: SpeedParameterActBinding? = null
    val binding get() = _binding!!
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var isChange = 0
    private var isShow = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SpeedParameterActBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }


    private fun initViews() {
        binding.spRate.withTremble = false
        binding.btChange100.setOnClickListener {
            binding.spRate.maxSpeed = 100f
            binding.spRate.unit = "Mb/s"
            if (isChange == 1) {
                a /= 5
            }
            binding.spRate.speedTo(a, 500)
            isChange = 0
        }
        binding.btChange500.setOnClickListener {
            binding.spRate.maxSpeed = 500f
            binding.spRate.unit = "Kb/s"
            if (isChange == 0) {
                a *= 5
            }
            binding.spRate.speedTo(a, 500)
            isChange = 1
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
                        binding.spRate.speedTo(a, 1000)
                    } else {
                        Log.d("TAG", "initViews: you can't set speed")
                    }

                }
            }
        }
    }




}