package com.android.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.myapplication.databinding.ActivityMainBinding
import com.android.myapplication.discharge.DischargeMainFragment
import com.android.myapplication.membership.MembershipMainFragment
import com.android.myapplication.my.MyMainFragment
import com.android.myapplication.store.StoreMainFragment

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private val handler = Handler(Looper.getMainLooper())

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
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