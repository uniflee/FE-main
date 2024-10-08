package com.android.myapplication.store

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityShoppingCartBinding

class ShoppingCartActivity : AppCompatActivity() {

    private var mbinding : ActivityShoppingCartBinding? = null
    private val binding get() = mbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mbinding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.purchaseBtn.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
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