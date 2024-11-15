package com.android.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityMainBinding
import com.android.myapplication.discharge.DischargeMainFragment
import com.android.myapplication.membership.MembershipMainFragment
import com.android.myapplication.my.MyMainFragment
import com.android.myapplication.store.StoreMainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private val handler = Handler(Looper.getMainLooper())

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 기본 정보 저장

        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getUserInfo(token)
                Log.e("API Response Main", response.toString())
                App.prefs.addItem("id", response.id)
                App.prefs.addItem("grade", response.grade)
                App.prefs.addItem("name", response.name)
                App.prefs.addItem("totalPoints", response.totalPoints)
                App.prefs.addItem("currentPoints", response.currentPoints)
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }

        // 처음 실행할 때 기본 Fragment 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DischargeMainFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_discharge -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DischargeMainFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.fragment_store -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, StoreMainFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.fragment_membership -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MembershipMainFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.fragment_my -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MyMainFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
        // 뒤로가기 버튼을 커스텀 처리
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity() // 앱 종료
                    return
                }

                doubleBackToExitPressedOnce = true
                Toast.makeText(this@MainActivity, "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()

                // 2초 후에 doubleBackToExitPressedOnce 변수를 false로 리셋
                handler.postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000)
            }
        })
    }
}