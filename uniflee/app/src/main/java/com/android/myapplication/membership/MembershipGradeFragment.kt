package com.android.myapplication.membership

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.myapplication.App
import com.android.myapplication.R
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.FragmentMembershipGradeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MembershipGradeFragment : Fragment() {
    private var _binding: FragmentMembershipGradeBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembershipGradeBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, MembershipMainFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getMembership(token)
                Log.e("API Response", response.toString())

                // 화면에 적용하기
                binding.root.post {

                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }


        return binding.root
    }
}