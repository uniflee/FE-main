package com.android.myapplication.membership

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.databinding.ActivityMembershipInfoBinding

class MembershipInfoActivity : AppCompatActivity() {
    private val binding: ActivityMembershipInfoBinding by lazy {
        ActivityMembershipInfoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}