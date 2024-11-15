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
        binding.itemType.text = "PP"
        binding.inst1.text = getInstructions(itemType = "PP")[1].toString()
        binding.inst2.text = getInstructions(itemType = "PP")[2].toString()
        binding.inst3.text = getInstructions(itemType = "PP")[3].toString()

        binding.goNewReward.setOnClickListener {
            val intent = Intent(this, DischargeNewRewardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getInstructions( itemType : String ): List<Any> {
        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"
        // 받을 정보
        var point = 0
        var ins1 = ""
        var ins2 = ""
        var ins3 = ""
        GlobalScope.launch (Dispatchers.IO){
            try {
                val response = apiService.getRecyclingGuide(token,itemType)
                point = response.point
                ins1 = response.disposalInstructions1
                ins2 = response.disposalInstructions2
                ins3 = response.disposalInstructions3

            } catch (e: Exception) {
                Log.e("Error", "Network request failed: ${e.message}")
            }
        }
        val rst = listOf(point,ins1,ins2,ins3)
        return rst
    }

}