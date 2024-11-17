package com.android.myapplication.my

import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.App
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.FragmentDischargeMainBinding
import com.android.myapplication.databinding.FragmentMembershipGradeBinding
import com.android.myapplication.databinding.FragmentMyMainBinding
import com.android.myapplication.discharge.DischargeCaptureActivity
import com.android.myapplication.dto.OrderListResponseDto
import com.android.myapplication.dto.OrderRecycler
import com.android.myapplication.dto.OrderRequestDto
import com.android.myapplication.dto.OrdersResponseDto
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.StringReader

class MyMainFragment : Fragment() {
    private var _binding: FragmentMyMainBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getOrderList(token)
                Log.e("API Response", response.toString())
                val cPoint = response.currentPoint.toString()

                binding.root.post {
                    binding.userName.text = App.prefs.getItem("name","noName")
                    binding.userCurrentPoint.text = cPoint

                }
            } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
            }
        }

        val recyclerView: RecyclerView = binding.recyclerView

        // 샘플 데이터 생성
        val itemList = listOf(
            OrderRecycler("24.10.29", "제품명1", "디자이너이름1", null, 1,6000),
            OrderRecycler("24.11.17", "제품명2", "디자이너이름2", null, 2,4000)
        )

        // 어댑터 연결
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = OrderAdapter(itemList)

        return binding.root
    }

}