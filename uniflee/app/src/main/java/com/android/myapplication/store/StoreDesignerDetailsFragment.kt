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

class StoreDesignerDetailsFragment : Fragment() {

    private var _binding: FragmentStoreDesignerDetailsBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreDesignerDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val itemList = listOf(
            ProductContents(R.drawable.bg_gray, "디자이너1", "상품1", 1111),
            ProductContents(R.drawable.bg_gray, "디자이너2", "상품2", 2222),
            ProductContents(R.drawable.bg_gray, "디자이너3", "상품3", 3333),
            ProductContents(R.drawable.bg_gray, "디자이너4", "상품4", 4444)
        )

        binding.rvDetailsProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvDetailsProduct.adapter = ProductAdapter(itemList) { item ->
            val intent = Intent(context, StoreProductDetailsActivity::class.java).apply {
                putExtra("PRODUCT_IMAGE", item.ProductImage)
                putExtra("STORE_NAME", item.StoreName)
                putExtra("PRODUCT_NAME", item.ProductName)
                putExtra("PRODUCT_PRICE", item.ProductPrice)
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}