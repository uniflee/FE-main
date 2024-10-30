package com.android.myapplication.discharge

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.myapplication.databinding.FragmentDischargeMainBinding

class DischargeMainFragment : Fragment() {
    private var _binding: FragmentDischargeMainBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDischargeMainBinding.inflate(inflater, container, false)
        binding.goCamera.setOnClickListener {
            startActivity(Intent(context, DischargeCaptureActivity()::class.java))
        }
        return binding.root
    }
}