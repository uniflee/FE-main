package com.android.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
        handleAppLink(intent)

        binding.loginBtn.setOnClickListener {
            openLoginWebPage()
        }

    }
    private fun handleAppLink(intent: Intent) {
        intent.data?.let { uri: Uri ->
            when (uri.path) {
                "/token" -> {
                    // URI의 쿼리 파라미터를 가져옴
                    val token = uri.getQueryParameter("t")
                    val rtoken = uri.getQueryParameter("r")
                    // 해당 화면으로 이동할 인텐트 생성
                    val intent = Intent(this, MainActivity::class.java).apply {
                        Log.e("token",token.toString())
                        // pref에 저장
                        App.prefs.addItem("token", token.toString())
                    }
                    startActivity(intent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAppLink(intent)
    }
    fun openLoginWebPage() {
        val url = "https://uniflee.alpha.cs.kookmin.ac.kr/oauth2/authorization/kookmin"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}