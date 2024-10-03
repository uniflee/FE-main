package com.android.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.myapplication.databinding.ActivityMainBinding
import com.android.myapplication.discharge.DischargeMainFragment
import com.android.myapplication.membership.MembershipMainFragment
import com.android.myapplication.my.MyMainFragment
import com.android.myapplication.store.StoreMainFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_discharge
        }
    }

    fun setBottomNavigationView() {

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_discharge -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, DischargeMainFragment()).commit()
                    true
                }
                R.id.fragment_store -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, StoreMainFragment()).commit()
                    true
                }
                R.id.fragment_membership -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, MembershipMainFragment()).commit()
                    true
                }
                R.id.fragment_my -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, MyMainFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}