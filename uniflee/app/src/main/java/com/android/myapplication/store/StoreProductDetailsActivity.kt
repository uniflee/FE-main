package com.android.myapplication.store

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.myapplication.App
import com.android.myapplication.R
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityStoreProductDetailsBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoreProductDetailsActivity : AppCompatActivity() {

    private var mbinding : ActivityStoreProductDetailsBinding? = null
    private val binding get() = mbinding!!

    private val token = "Bearer ${App.prefs.getItem("token", "")}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mbinding = ActivityStoreProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getIntExtra("ITEM_ID", -1)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        switchToFragment(StoreProductInfoFragment(), itemId)
        ActiveBtn(binding.tvInfobtn, binding.underlineInfo)

        binding.tvInfobtn.setOnClickListener{
            switchToFragment(StoreProductInfoFragment(), itemId)
            ActiveBtn(binding.tvInfobtn, binding.underlineInfo)
        }

        binding.tvReceiptbtn.setOnClickListener{
            switchToFragment(StoreReceiptInfoFragment(), itemId)
            ActiveBtn(binding.tvReceiptbtn, binding.underlineReceipt)
        }

        GlobalScope.launch {
            try{
                Log.d("StoreProductDetailsActivity", "itemId: ${itemId}")
                val response = RetrofitClient.apiservice.getItemDetail(token, itemId)
                val finalImageUrl = "https://uniflee.alpha.cs.kookmin.ac.kr/uniflee-simple-storage-service/" + response.featuredImageUrl
                withContext(Dispatchers.Main) {
                    Glide.with(this@StoreProductDetailsActivity)
                        .load(finalImageUrl)
                        .into(binding.featureImage)
                    binding.designerName.text = response.designerName
                    binding.name.text = response.name
                    binding.price.text = "${response.price} 포인트"
                }
            }catch (e: retrofit2.HttpException){
                Log.e("StoreProductDetailsActivity", "Image Load Failed!: ${e.response()?.errorBody()?.string()}")
            }
        }

    }

    private fun switchToFragment(fragment: Fragment, itemId: Int) {
        val bundle = Bundle()
        bundle.putInt("ITEM_ID", itemId)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun ActiveBtn(activeBtn: TextView, activeUnderline: View) {
        binding.tvInfobtn.setTextColor(Color.parseColor("#C5C5C5"))
        binding.tvReceiptbtn.setTextColor(Color.parseColor("#C5C5C5"))

        binding.underlineInfo.setBackgroundColor(Color.parseColor("#00FF0000"))
        binding.underlineReceipt.setBackgroundColor(Color.parseColor("#00FF0000"))

        activeBtn.setTextColor(Color.parseColor("#3E3E3E"))
        activeUnderline.setBackgroundColor(Color.parseColor("#3E3E3E"))
    }
}