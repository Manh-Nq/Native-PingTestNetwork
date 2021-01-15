package com.tapi.speedtest

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.databinding.SpeedParameterActBinding
import com.tapi.speedtest.speedview.anim.ArcAngleAnimation
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

        binding.spRate.withTremble = false
        val animation = ArcAngleAnimation(
            binding.spRate,
            Constance.MAX_ANGLE
        )
        animation.duration = 1000
        binding.spRate.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.spRate.withTremble = true
                myScope.launch {
                    while (true) {
                        val random = Random.nextInt(61) + 20
                        Log.d("TAG", "initViews: $random")
                        delay(2500)
                        withContext(Dispatchers.Main) {

                            binding.spRate.speedTo(random * 1f)
                        }
                    }
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })


        /*  binding.btgo.setOnClickListener {
              binding.spRate.maxSpeed = 1000f
              binding.spRate.unit = "Mb/s"
          }*/
    }




}