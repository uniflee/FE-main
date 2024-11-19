package com.android.myapplication.store

import android.net.http.HttpException
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.App
import com.android.myapplication.R
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityStorePaymentBinding
import com.android.myapplication.dto.OrderRequestDto
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class StorePaymentActivity : AppCompatActivity() {

    private var mbinding : ActivityStorePaymentBinding? = null
    private val binding get() = mbinding!!

    private val token = "Bearer ${App.prefs.getItem("token", "")}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mbinding = ActivityStorePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getIntExtra("ITEM_ID", -1)

        GlobalScope.launch {
            val prod = RetrofitClient.apiservice.getItemDetail(token, itemId)
            val curpoint = App.prefs.getItem("currentPoints", "0000")
            val finalImageUrl = "https://uniflee.alpha.cs.kookmin.ac.kr/uniflee-simple-storage-service/" + prod.featuredImageUrl
            withContext(Dispatchers.Main) {
                Glide.with(binding.root)
                    .load(finalImageUrl)
                    .into(binding.productImage)
                binding.designerName.text = prod.designerName
                binding.name.text = prod.name
                binding.price.text = "${prod.price} 포인트"
                binding.totalPoint.text = "${prod.price} 포인트"
                binding.currentPoint.text = "보유 포인트 ${curpoint}pt"
            }
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.payBtn.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.apiservice.newOrder(token, OrderRequestDto(itemId, 1))
                    }
                    Log.d("StorePaymentActivity", "주문 성공: $response")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@StorePaymentActivity, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } catch (e: retrofit2.HttpException) {
                    Log.e("StorePaymentActivity", "주문 실패: ${e.response()?.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@StorePaymentActivity, "주문 처리 중 오류가 발생했습니다", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("StorePaymentActivity", "예외 발생", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@StorePaymentActivity, "네트워크 오류가 발생했습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}