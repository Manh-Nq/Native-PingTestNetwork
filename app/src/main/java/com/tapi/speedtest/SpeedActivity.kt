package com.tapi.speedtest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
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
            if (isChange == 2) {
                a /= 10
            }
            binding.spRate.speedTo(a, 0)
            isChange = 0
        }
        binding.btChange500.setOnClickListener {
            binding.spRate.maxSpeed = 500f
            binding.spRate.unit = "Kb/s"
            if (isChange == 0) {
                a *= 5
            }
            if (isChange == 2) {
                a /= 2
            }
            binding.spRate.speedTo(a, 0)
            isChange = 1
        }
        binding.btChange1000.setOnClickListener {
            binding.spRate.maxSpeed = 1000f
            binding.spRate.unit = "Bit/s"
            if (isChange == 0) {
                a *= 5
            }
            if (isChange == 1) {
                a *= 2
            }
            binding.spRate.speedTo(a, 0)
            isChange = 2
        }


        binding.btTest.setOnClickListener {
            if (isShow) {
                binding.spRate.visibility = View.VISIBLE
                lifecycleScope.launchWhenResumed {
                    binding.spRate.start()
                }
            } else {
                binding.spRate.setRatioAlpha(0)
                binding.spRate.withTremble = false
                binding.spRate.visibility = View.GONE
                binding.spRate.resetLayoutView()
            }
            isShow = !isShow

        }

        binding.btSpeed.setOnClickListener {
            myScope.launch {
                a = (Random.nextInt(61) + 20f)
                withContext(Dispatchers.Main) {
                    binding.spRate.speedTo(a, 1000)
                    delay(5000)
                    binding.spRate.speedTo(0f)
                }
            }
        }
    }




}