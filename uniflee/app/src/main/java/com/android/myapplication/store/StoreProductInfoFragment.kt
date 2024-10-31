package com.android.myapplication.store

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentStoreProductInfoBinding
import com.android.myapplication.store.StorePaymentActivity

class StoreProductInfoFragment : Fragment() {

    private var _binding: FragmentStoreProductInfoBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStoreProductInfoBinding.inflate(inflater, container, false)

        binding.purchaseBtn.setOnClickListener {
            startActivity(Intent(context, StorePaymentActivity()::class.java))
        }

        val itemList = listOf(
            InfoContents(R.drawable.img_productimage, "첫 번째 상품입니다. \n 반가워요!"),
            InfoContents(R.drawable.img_productimage, "두 번째 상품입니다. \n 안녕하세요!")
        )

        binding.rvInfo.layoutManager = LinearLayoutManager(context)
        binding.rvInfo.adapter = InfoAdapter(itemList)

        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}