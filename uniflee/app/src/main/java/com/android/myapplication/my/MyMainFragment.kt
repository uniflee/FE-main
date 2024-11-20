package com.android.myapplication.my

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.App
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.FragmentMyMainBinding
import com.android.myapplication.dto.OrderRecycler
import com.android.myapplication.dto.OrdersResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyMainFragment : Fragment() {
    private var _binding: FragmentMyMainBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.recyclerView

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
                // 서버 데이터를 RecyclerView 데이터로 변환
                val itemList = mapResponseToRecycler(response.ordersResponseDtoList)
                Log.e("LLLLIIIISST",itemList.toString())

                withContext(Dispatchers.Main) {
                    binding.userName.text = App.prefs.getItem("name", "noName")
                    binding.userCurrentPoint.text = response.currentPoint.toString()

                    // 어댑터 연결
                    recyclerView.adapter = OrderAdapter(itemList)
                }

            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }

//        // 샘플 데이터 생성
//        val itemList = listOf(
//            OrderRecycler("24.10.29", "제품명1", "디자이너이름1", null, 1,6000),
//            OrderRecycler("24.11.17", "제품명2", "디자이너이름2", null, 2,4000)
//        )

        return root
    }
    // 서버 데이터를 화면 데이터로 변환하는 함수
    private fun mapResponseToRecycler(ordersResponseDtoList: List<OrdersResponseDto>): List<OrderRecycler> {
        return ordersResponseDtoList.map { response ->
            Log.e("date",response.createdAt)
            OrderRecycler(
                date = convertDateFormat(response.createdAt), // 날짜 임의로 지정
                name = response.name,            // 제품명
                designerName = response.designerName, // 디자이너 이름
                featuredImageUrl = response.featuredImageUrl, // 이미지 URL
                count = response.count,       // 수량
                point = response.point      // 포인트
            )
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun convertDateFormat(date: String): String {
        // 원래 문자열을 LocalDateTime 객체로 파싱
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val dateTime = LocalDateTime.parse(date, inputFormatter)

        // 원하는 형식으로 변환
        val outputFormatter = DateTimeFormatter.ofPattern("yy.MM.dd")
        return dateTime.format(outputFormatter)
    }

}