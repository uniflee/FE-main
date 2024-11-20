package com.android.myapplication.store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.App
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.FragmentStoreProductInfoBinding
import com.android.myapplication.dto.ItemDescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoreProductInfoFragment : Fragment() {

    private var _binding: FragmentStoreProductInfoBinding?=null
    private val binding get() = _binding!!

    private var itemId: Int = -1
    private var itemList: MutableList<ItemDescription> = mutableListOf()

    private val token = "Bearer ${App.prefs.getItem("token", "")}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 프래그먼트 arguments에서 itemId 가져오기
        itemId = arguments?.getInt("ITEM_ID", -1) ?: -1
        Log.d("StoreProductInfoFragment", "Received itemId: $itemId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStoreProductInfoBinding.inflate(inflater, container, false)

        binding.purchaseBtn.setOnClickListener {
            val intent = Intent(context, StorePaymentActivity::class.java)
            intent.putExtra("ITEM_ID", itemId)
            startActivity(intent)
        }

        GlobalScope.launch {
            try{
                withContext(Dispatchers.Main) {
                    itemList = RetrofitClient.apiservice.getItemDetail(token, itemId).descriptions
                    binding.rvInfo.layoutManager = LinearLayoutManager(context)
                    binding.rvInfo.adapter = InfoAdapter(itemList)
                }
            }catch (e: retrofit2.HttpException){
                Log.e("StoreProductInfoFragment", "Image Load Failed!: ${e.response()?.errorBody()?.string()}")
            }
        }



        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}