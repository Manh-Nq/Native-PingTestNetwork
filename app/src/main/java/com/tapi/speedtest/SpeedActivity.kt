package com.tapi.speedtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tapi.speedtest.databinding.SpeedParameterActBinding
import kotlinx.coroutines.*
import kotlin.random.Random

class SpeedActivity : AppCompatActivity() {
    private var _binding: SpeedParameterActBinding? = null
    val binding get() = _binding!!
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SpeedParameterActBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.spRate.withTremble = true
        myScope.launch {
            while (true) {
                val random = Random.nextInt(61) + 20
                Log.d("TAG", "initViews: $random")
                delay(2500)
                withContext(Dispatchers.Main){

                    binding.spRate.speedTo(random * 1f)
                }
            }
        }
        binding.spRate.speedTo(100f)

        /*CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                binding.spRate.speedTo(random.nextInt().toFloat(),1000)
            }
        }*/

        binding.btgo.setOnClickListener {
            binding.spRate.maxSpeed = 1000f
            binding.spRate.unit = "Mb/s"
        }
    }


}