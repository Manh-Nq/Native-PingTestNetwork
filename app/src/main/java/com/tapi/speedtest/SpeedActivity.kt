package com.tapi.speedtest

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.databinding.SpeedParameterActBinding
import com.tapi.speedtest.speedview.anim.ArcAngleAnimation
import kotlinx.coroutines.*
import kotlin.random.Random

class SpeedActivity : AppCompatActivity(), Animation.AnimationListener {
    private var a: Float = 0f
    private var animation: ArcAngleAnimation? = null
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
                creatAnimation()
                binding.spRate.visibility = View.VISIBLE
                binding.spRate.startAnimation(animation)
            } else {
                binding.spRate.withTremble = false
                binding.spRate.visibility = View.GONE
                animation?.setArcAngle()
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
        animation?.setAnimationListener(this)
    }

    private fun creatAnimation() {
        animation = ArcAngleAnimation(
            binding.spRate,
            Constance.MAX_ANGLE
        )
        animation?.duration = 1000
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        /*binding.spRate.withTremble = true*/
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }


}