package com.android.myapplication.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentMembershipBenefitBinding
import com.android.myapplication.databinding.FragmentMembershipTipsBinding

class MembershipBenefitFragment : Fragment() {
    private var _binding: FragmentMembershipBenefitBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembershipBenefitBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}