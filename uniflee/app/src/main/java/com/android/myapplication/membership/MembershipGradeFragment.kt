package com.android.myapplication.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentMembershipGradeBinding

class MembershipGradeFragment : Fragment() {
    private var _binding: FragmentMembershipGradeBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembershipGradeBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, MembershipMainFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return binding.root
    }
}