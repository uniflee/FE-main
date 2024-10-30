package com.android.myapplication.discharge

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityDischargeGuideBinding
import com.android.myapplication.databinding.ActivityDischargeNewRewardBinding

class DischargeNewRewardActivity : AppCompatActivity() {
    private val binding: ActivityDischargeNewRewardBinding by lazy {
        ActivityDischargeNewRewardBinding.inflate(layoutInflater)
    }
    private var number = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        val textNumber = binding.textNumber
        val buttonMinus = binding.buttonMinus
        val buttonPlus = binding.buttonPlus

        // 초기 숫자 설정
        textNumber.text = number.toString()

        // 마이너스 버튼 클릭 시
        buttonMinus.setOnClickListener {
            if (number>0){
                number--
            }
            textNumber.text = number.toString()
        }

        // 플러스 버튼 클릭 시
        buttonPlus.setOnClickListener {
            if (number<5){
                number++
            }
            textNumber.text = number.toString()
        }
    }
}