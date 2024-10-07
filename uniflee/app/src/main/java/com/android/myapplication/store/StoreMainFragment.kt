package com.android.myapplication.store

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentStoreMainBinding


class StoreMainFragment : Fragment() {

    private var _binding: FragmentStoreMainBinding?=null
    private val binding get() = _binding!!

    private val productitems = mutableListOf<ProductContents>()
    private val designeritems = mutableListOf<ProductContents>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreMainBinding.inflate(inflater, container, false)
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