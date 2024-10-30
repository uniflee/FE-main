package com.android.myapplication.discharge

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityDischargeGuideBinding
import com.android.myapplication.databinding.ActivityMembershipBenefitBinding

class DischargeGuideActivity : AppCompatActivity() {
    private val binding: ActivityDischargeGuideBinding by lazy {
        ActivityDischargeGuideBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.goNewReward.setOnClickListener {
            val intent = Intent(this, DischargeNewRewardActivity::class.java)
            startActivity(intent)
        }
    }
}