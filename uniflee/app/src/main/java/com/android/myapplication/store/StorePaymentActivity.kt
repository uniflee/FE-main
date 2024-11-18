package com.android.myapplication.store

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityStorePaymentBinding

class StorePaymentActivity : AppCompatActivity() {

    private var mbinding : ActivityStorePaymentBinding? = null
    private val binding get() = mbinding!!

    private val productList = mutableListOf<PaymentContents>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mbinding = ActivityStorePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        productList.addAll(
            listOf(
                PaymentContents(R.drawable.img_productimage, "디자이너1", "상품1", 1000),
                PaymentContents(R.drawable.img_productimage, "디자이너2", "상품2", 2000)
            )
        )

        binding.rvPay.layoutManager = LinearLayoutManager(this)
        binding.rvPay.adapter = PaymentAdapter(productList)

        updateProductCount(productList.size)
        updateTotalPoints()
    }

    private fun updateTotalPoints() {
        val totalPoints = productList.sumOf { it.ProductPrice }
        binding.totalPoint.text = "${totalPoints} 포인트"
    }

    private fun updateProductCount(count: Int) {
        binding.tvProductCount.text = "주문 상품 ${count}개"
    }
}