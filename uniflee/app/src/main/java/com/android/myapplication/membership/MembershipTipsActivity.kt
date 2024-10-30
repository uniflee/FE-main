package com.android.myapplication.membership

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.MainActivity
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityMainBinding
import com.android.myapplication.databinding.ActivityMembershipTipsBinding

class MembershipTipsActivity : AppCompatActivity() {
    private val binding: ActivityMembershipTipsBinding by lazy {
        ActivityMembershipTipsBinding.inflate(layoutInflater)
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