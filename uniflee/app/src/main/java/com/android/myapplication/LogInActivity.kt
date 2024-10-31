package com.android.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityLogInBinding
import com.android.myapplication.databinding.ActivityMembershipBenefitBinding
import com.android.myapplication.discharge.DischargeNewRewardActivity
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.net.HttpURLConnection

class LogInActivity : AppCompatActivity() {
    private val binding: ActivityLogInBinding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }
    // api 연결
    val apiService = RetrofitClient.apiservice
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {
            openLoginWebPage()
            GlobalScope.launch(Dispatchers.IO) {
                val res = apiService.userLogin()
                Log.e("응답", res.toString())
            }
            // 앱 링크 데이터 처리
            handleAppLink(intent)
        }

    }
    fun openLoginWebPage() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val responseData = apiService.getLoginUrl()
                val url = "https://uniflee.alpha.cs.kookmin.ac.kr/oauth2/authorization/kookmin"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                Log.e("200", responseData.toString())

            } catch (e:Exception) {
                Log.e("Error", e.message.toString())
            }
        }

    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAppLink(intent)
    }

    private fun handleAppLink(intent: Intent) {
        intent.data?.let { uri: Uri ->
            // 여기서 uri를 처리하는 코드 작성
            // 예: 특정 화면으로 이동, 데이터 로드 등
        }
    }
}