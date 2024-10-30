package com.android.myapplication.store

import android.graphics.Color
import android.os.Bundle
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

        switchToFragment1()

        binding.tvInfobtn.setOnClickListener{
            switchToFragment1()
        }

        binding.tvReceiptbtn.setOnClickListener {
            switchToFragment2()
        }
    }
    private fun switchToFragment1() {
        replaceFragment(StoreProductInfoFragment())

        binding.tvInfobtn.setTextColor(Color.BLACK)
        binding.tvReceiptbtn.setTextColor(Color.parseColor("#C5C5C5"))
    }

    private fun switchToFragment2() {
        replaceFragment(StoreReceiptInfoFragment())

        binding.tvInfobtn.setTextColor(Color.BLACK)
        binding.tvReceiptbtn.setTextColor(Color.parseColor("#C5C5C5"))
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}