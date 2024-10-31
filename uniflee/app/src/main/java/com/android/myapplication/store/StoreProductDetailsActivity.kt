package com.android.myapplication.store

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityStoreProductDetailsBinding

class StoreProductDetailsActivity : AppCompatActivity() {

    private var mbinding : ActivityStoreProductDetailsBinding? = null
    private val binding get() = mbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mbinding = ActivityStoreProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        switchToFragment(StoreProductInfoFragment())
        ActiveBtn(binding.tvInfobtn, binding.underlineInfo)

        binding.tvInfobtn.setOnClickListener{
            switchToFragment(StoreProductInfoFragment())
            ActiveBtn(binding.tvInfobtn, binding.underlineInfo)
        }

        binding.tvReceiptbtn.setOnClickListener{
            switchToFragment(StoreReceiptInfoFragment())
            ActiveBtn(binding.tvReceiptbtn, binding.underlineReceipt)
        }

    }

    private fun switchToFragment(fragment: Fragment) {
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