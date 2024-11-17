package com.android.myapplication.discharge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.App
import com.android.myapplication.MainActivity
import com.android.myapplication.R
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityDischargeGuideBinding
import com.android.myapplication.databinding.ActivityDischargeNewRewardBinding
import com.android.myapplication.dto.RecyclingRequestDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DischargeNewRewardActivity : AppCompatActivity() {
    private val binding: ActivityDischargeNewRewardBinding by lazy {
        ActivityDischargeNewRewardBinding.inflate(layoutInflater)
    }
    private var number = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // 백버튼 누를시
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // 이전에 받아온거
        val receivedPoint = intent.getStringExtra("point")
        val receivedPredict = intent.getStringExtra("predict")
        binding.point.text = receivedPoint
        binding.predict.text = receivedPredict

        val textNumber = binding.textNumber
        val buttonMinus = binding.buttonMinus
        val buttonPlus = binding.buttonPlus
        val totalPoint = binding.totalPoint

        // 초기 숫자 설정
        textNumber.text = number.toString()
        totalPoint.text = "0"

        // 마이너스 버튼 클릭 시
        buttonMinus.setOnClickListener {
            if (number>0){
                number--
            }
            textNumber.text = number.toString()
            totalPoint.text = (receivedPoint?.toInt()?.times(number)).toString()
        }

        // 플러스 버튼 클릭 시
        buttonPlus.setOnClickListener {
            if (number<5){
                number++
            }
            textNumber.text = number.toString()
            totalPoint.text = (receivedPoint?.toInt()?.times(number)).toString()
        }

        binding.getReward.setOnClickListener {
            if (receivedPredict != null) {
                requestNewReward(receivedPredict, (textNumber.text as String).toInt())
            }
        }
    }

    fun requestNewReward( predict : String, count : Int){
        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.newRecycling(token, RecyclingRequestDto(predict,count))
                Log.e("API response", response)
                intentMain()
            } catch (e: Exception) {
                Log.e("Error", "Network request failed: ${e.message}")
            }
        }

    }

    fun intentMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}