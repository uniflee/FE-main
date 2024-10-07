package com.android.myapplication.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentDesignerDetailsBinding
import com.android.myapplication.databinding.FragmentStoreMainBinding

class DesignerDetailsFragment : Fragment() {

    private var _binding: FragmentDesignerDetailsBinding?=null
    private val binding get() = _binding!!

    private val items = mutableListOf<ProductContents>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDesignerDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.goGradeBtn.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, MembershipGradeFragment())
//                .addToBackStack(null) // 백스택에 추가해서 뒤로 가기 가능
//                .commitAllowingStateLoss()
//        }
//        binding.goGradeInfo.setOnClickListener {
//            startActivity(Intent(context, MembershipInfoActivity()::class.java))
//        }

        return root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}