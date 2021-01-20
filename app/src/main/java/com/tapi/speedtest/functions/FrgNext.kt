package com.tapi.speedtest.functions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tapi.speedtest.databinding.FragmentFrgNextBinding

class FrgNext : Fragment() {

    private var _binding: FragmentFrgNextBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFrgNextBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.btBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}