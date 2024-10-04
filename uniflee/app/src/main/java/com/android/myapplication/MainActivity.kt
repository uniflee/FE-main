package com.android.myapplication

import android.os.Bundle
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
    }
}