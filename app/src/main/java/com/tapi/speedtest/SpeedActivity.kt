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
    private var animation: ArcAngleAnimation? = null
    private var _binding: SpeedParameterActBinding? = null
    val binding get() = _binding!!
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var isChange = true
    private var isShow = true
    private var myJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SpeedParameterActBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.spRate.withTremble = false
        animation = ArcAngleAnimation(
            binding.spRate,
            Constance.MAX_ANGLE
        )
        animation?.duration = 1000

        animation?.setAnimationListener(this)


        binding.btChange.setOnClickListener {
            if (isChange) {
                binding.spRate.maxSpeed = 1000f
                binding.spRate.unit = "Mb/s"
            } else {
                binding.spRate.maxSpeed = 100f
                binding.spRate.unit = "Mbps/s"
            }
            isChange = !isChange
        }

        binding.btT.setOnClickListener {
//            binding.spRate.setArcAngle(0f)
            if (isShow) {
                binding.spRate.visibility = View.VISIBLE
                binding.spRate.startAnimation(animation)
            } else {
                binding.spRate.withTremble = false
                binding.spRate.visibility = View.GONE
                myJob?.cancel()
            }
            isShow = !isShow


        }
    }

    override fun onAnimationStart(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {
        binding.spRate.withTremble = true
        myJob = myScope.launch {
            while (true) {
                val random = Random.nextInt(61) + 20
                delay(2500)
                withContext(Dispatchers.Main) {
                    binding.spRate.speedTo(random * 1f)
                }
            }
        }
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }


}