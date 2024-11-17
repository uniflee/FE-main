package com.android.myapplication.discharge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.App
import com.android.myapplication.R
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityDischargeGuideBinding
import com.android.myapplication.databinding.ActivityMembershipBenefitBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DischargeGuideActivity : AppCompatActivity() {
    private val binding: ActivityDischargeGuideBinding by lazy {
        ActivityDischargeGuideBinding.inflate(layoutInflater)
    }
    var point: Int? = null // 외부에 Mutable 변수 선언
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        val receivedPredict = intent.getStringExtra("predict")

        if (receivedPredict != null) {
            getInstructions(receivedPredict) { apiresponse ->
                binding.itemType.text = receivedPredict
                binding.inst1.text = apiresponse?.get(1) as String
                binding.inst2.text = apiresponse.get(2) as String
                binding.inst3.text = apiresponse.get(3) as String
                point = apiresponse[0] as Int
            }
        }


        binding.goNewReward.setOnClickListener {
            val intent = Intent(this, DischargeNewRewardActivity::class.java)
            intent.putExtra("point",point.toString())
            intent.putExtra("predict",receivedPredict)
            startActivity(intent)
        }
    }

    private fun getInstructions(itemType: String, callback: (List<Any>?) -> Unit) {
        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getRecyclingGuide(token, itemType)
                Log.e("API response", response.toString())

                val point = response.point
                val ins1 = response.disposalInstructions1
                val ins2 = response.disposalInstructions2
                val ins3 = response.disposalInstructions3

                val rst = listOf(point, ins1, ins2, ins3)

                withContext(Dispatchers.Main) {
                    callback(rst) // 결과 전달
                }
            } catch (e: Exception) {
                Log.e("Error", "Network request failed: ${e.message}")
                withContext(Dispatchers.Main) {
                    callback(null) // 실패 시 null 전달
                }
            }
        }
    }


}