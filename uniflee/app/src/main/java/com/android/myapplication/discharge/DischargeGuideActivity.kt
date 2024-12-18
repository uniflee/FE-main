package com.android.myapplication.discharge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.App
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityDischargeGuideBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DischargeGuideActivity : AppCompatActivity() {
    private val binding: ActivityDischargeGuideBinding by lazy {
        ActivityDischargeGuideBinding.inflate(layoutInflater)
    }
    var point: Int? = null // 외부에 Mutable 변수 선언
    var co2: Double? = null
    var displayName = ""
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
                binding.itemType.text = apiresponse?.get(2) as String
                displayName = apiresponse?.get(2) as String
                binding.inst1.text = apiresponse.get(3) as String
                binding.inst2.text = apiresponse.get(4) as String
                binding.inst3.text = apiresponse.get(5) as String
                point = apiresponse[0] as Int
                co2 = apiresponse[1] as Double

            }
        }

        binding.goNewReward.setOnClickListener {
            if (receivedPredict != null) {
                IntentNext(displayName,receivedPredict)
            }
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
                val co2 = response.co2
                val displayName = response.displayName
                val ins1 = response.disposalInstructions1
                val ins2 = response.disposalInstructions2
                val ins3 = response.disposalInstructions3


                val rst = listOf(point, co2, displayName,ins1, ins2, ins3)

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

    private fun IntentNext(displayName: String,receivedPredict:String) {
        val intent = Intent(this, DischargeNewRewardActivity::class.java)
        intent.putExtra("point",point.toString())
        intent.putExtra("predict",receivedPredict)
        intent.putExtra("displayName",displayName)
        intent.putExtra("co2",co2.toString())
        startActivity(intent)
    }


}