package com.android.myapplication.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.myapplication.App
import com.android.myapplication.R
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.FragmentStoreMainBinding
import com.android.myapplication.dto.ItemResponseDto
import com.android.myapplication.membership.MembershipGradeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoreMainFragment : Fragment() {

    private var _binding: FragmentStoreMainBinding?=null
    private val binding get() = _binding!!

    private var itemList: MutableList<ItemResponseDto> = mutableListOf()

    private val token = "Bearer ${App.prefs.getItem("token", "")}"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.goGradeBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MembershipGradeFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.backgroundImageView.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, StoreDesignerDetailsFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        GlobalScope.launch {
            try{
                Log.d("StoreMainFragment", "token: ${token}")
                itemList = RetrofitClient.apiservice.getItemList(token)
                Log.d("StoreMainFragment", "itemList : ${itemList}")
                withContext(Dispatchers.Main){
                    val productAdapter = ProductAdapter(itemList)
                    binding.rvGoods.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.rvGoods.adapter = productAdapter
                }
            } catch (e: retrofit2.HttpException){
                Log.e("StoreMainFragment", "ItemList Load Failed!: ${e.response()?.errorBody()?.string()}")
            }
        }

        return root
    }


    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}