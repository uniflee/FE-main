package com.android.myapplication.store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.myapplication.App
import com.android.myapplication.R
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.FragmentStoreDesignerDetailsBinding
import com.android.myapplication.dto.ItemResponseDto
import com.android.myapplication.membership.MembershipMainFragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            transaction.commit()
        }

        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"


        GlobalScope.launch {
            try {
                val response = apiService.getDesignerInfo(token)
                Log.d("StoreDesignerDetailsFragment", "Designer Info: ${response}")
                itemList = apiService.getItemList(token)
                Log.d("StoreDesignerDetailsFragment", "Item Info: ${itemList}")
                withContext(Dispatchers.Main){
                    val pfImageUrl = "https://uniflee.alpha.cs.kookmin.ac.kr/uniflee-simple-storage-service/" + response.profileImageUrl
                    val bgImageUrl = "https://uniflee.alpha.cs.kookmin.ac.kr/uniflee-simple-storage-service/" + response.backgroundImageUrl
                    Glide.with(binding.root)
                        .load(pfImageUrl)
                        .into(binding.profileImg)
                    Glide.with(binding.root)
                        .load(bgImageUrl)
                        .into(binding.backgroundImageView)
                    binding.rvDetailsProduct.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.rvDetailsProduct.adapter = ProductAdapter(itemList)
                    binding.total.text = "총 ${ProductAdapter(itemList).itemCount}개의 디자인"
                }
            } catch (e: retrofit2.HttpException){
                Log.e("StoreDesignerDetailsFragment", "Info Load Failed!: ${e.response()?.errorBody()?.string()}")
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}