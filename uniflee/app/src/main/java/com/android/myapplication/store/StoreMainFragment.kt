package com.android.myapplication.store

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.R
import com.android.myapplication.databinding.FragmentStoreMainBinding
import com.android.myapplication.membership.MembershipGradeFragment


class StoreMainFragment : Fragment() {

    private var _binding: FragmentStoreMainBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.goGradeBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MembershipGradeFragment())
                .addToBackStack(null) // 백스택에 추가해서 뒤로 가기 가능
                .commitAllowingStateLoss()
        }

        binding.rvDesigner.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DesignerDetailsFragment())
                .addToBackStack(null) // 백스택에 추가해서 뒤로 가기 가능
                .commitAllowingStateLoss()
        }
        binding.rvGoods.setOnClickListener {
            startActivity(Intent(context, ProductDetailsActivity()::class.java))
        }

        binding.shoppingcartBtn.setOnClickListener {
            startActivity(Intent(context, ShoppingCartActivity()::class.java))
        }

        val itemList = listOf(
            ProductContents(R.drawable.bg_gray, "디자이너1", "상품1", 1111),
            ProductContents(R.drawable.bg_gray, "디자이너2", "상품2", 2222),
            ProductContents(R.drawable.bg_gray, "디자이너3", "상품3", 3333),
            ProductContents(R.drawable.bg_gray, "디자이너4", "상품4", 4444)
        )

        binding.rvGoods.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGoods.adapter = ProductAdapter(itemList)

        val DesignerList = listOf(
            DesignerContents(1, R.drawable.img_designerprofile, "미친 감각 디자이너 1"),
            DesignerContents(2, R.drawable.img_designerprofile, "예술의 경지 디자이너 2"),
            DesignerContents(3, R.drawable.img_designerprofile, "기상천외한 발상 디자이너 3")
        )

        binding.rvDesigner.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDesigner.adapter = DesignerAdapter(DesignerList)

        return root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}