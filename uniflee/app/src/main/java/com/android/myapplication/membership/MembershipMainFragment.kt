package com.android.myapplication.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentMembershipMainBinding


class MembershipMainFragment : Fragment() {
    private var _binding: FragmentMembershipMainBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembershipMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.goGradeBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MembershipGradeFragment())
                .addToBackStack(null) // 백스택에 추가해서 뒤로 가기 가능
                .commitAllowingStateLoss()
        }
        binding.goGradeInfo.setOnClickListener {

        }
        binding.goGradeTips.setOnClickListener {

        }
        binding.goGradeBenefit.setOnClickListener {

        }
        return root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}