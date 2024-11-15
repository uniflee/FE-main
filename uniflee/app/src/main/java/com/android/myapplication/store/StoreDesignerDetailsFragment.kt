package com.android.myapplication.store

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentStoreDesignerDetailsBinding
import com.android.myapplication.dto.ItemResponseDto
import com.android.myapplication.membership.MembershipMainFragment

class StoreDesignerDetailsFragment : Fragment() {

    private var _binding: FragmentStoreDesignerDetailsBinding?=null
    private val binding get() = _binding!!

    private var itemList: MutableList<ItemResponseDto> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreDesignerDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.backBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, StoreMainFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.rvDetailsProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvDetailsProduct.adapter = ProductAdapter(itemList)

        return root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}