package com.android.myapplication.membership

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityMembershipBenefitBinding
import com.android.myapplication.databinding.ActivityMembershipTipsBinding

class MembershipBenefitActivity : AppCompatActivity() {
    private val binding: ActivityMembershipBenefitBinding by lazy {
        ActivityMembershipBenefitBinding.inflate(layoutInflater)
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