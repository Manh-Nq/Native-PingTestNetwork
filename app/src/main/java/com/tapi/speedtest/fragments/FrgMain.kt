package com.tapi.speedtest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tapi.speedtest.R
import com.tapi.speedtest.databinding.FragmentFrgMainBinding

class FrgMain : Fragment() {

    var _binding: FragmentFrgMainBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFrgMainBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.btSpView.setOnClickListener {
            findNavController().navigate(R.id.action_frgMain_to_frgSpeed)
        }
        binding.btConnectView.setOnClickListener {
            findNavController().navigate(R.id.action_frgMain_to_frgNext)
        }
        binding.btPing.setOnClickListener {
            findNavController().navigate(R.id.action_frgMain_to_frgPing)
        }

        binding.btUpload.setOnClickListener {
            findNavController().navigate(R.id.action_frgMain_to_uploadFrg)
        }
        binding.btLineView.setOnClickListener {
            findNavController().navigate(R.id.action_frgMain_to_frgSpeedLinev)
        }

    }


}