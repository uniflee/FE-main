package com.android.myapplication.store

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityStoreCartBinding

class StoreCartActivity : AppCompatActivity() {

    private var mbinding : ActivityStoreCartBinding? = null
    private val binding get() = mbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mbinding = ActivityStoreCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.purchaseBtn.setOnClickListener {
            val intent = Intent(this, StorePaymentActivity::class.java)
            startActivity(intent)
        }

        val itemList = listOf(
            CartContents(false, "디자이너1", R.drawable.img_productimage, "상품1", 1000, 0 ),
            CartContents(true, "디자이너2", R.drawable.img_productimage, "상품2", 2000, 1 )
        )

        binding.rvCart.layoutManager = LinearLayoutManager(this)
        binding.rvCart.adapter = CartAdapter(itemList)
    }
}