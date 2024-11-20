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
                val impact = response.gradeImpact
                val grade = response.grade
                val totalPoint = response.totalPoints
                val leftPoint = gradeAndNext(totalPoint)[2].toString()
                val pb = binding.progressbar
                Log.e("now",totalPoint.toString())
                Log.e("left",leftPoint.toString())
                // 화면에 적용하기
                binding.root.post {
                    binding.userName.text = App.prefs.getItem("name","noName")
                    binding.treeProtected.text = impact.treesProtected
                    binding.totalPoint.text = totalPoint.toString()
                    binding.nextGrade.text = gradeAndNext(totalPoint)[1].toString()
                    binding.leftToNext.text = "다음 등급까지 $leftPoint pt"
                    pb.progress = gradeAndNext(totalPoint)[3] as Int
                    Log.e("for PB", gradeAndNext(totalPoint)[3].toString())

                    if (grade == "Bronze"){
                        binding.gradeImage.setImageResource(R.drawable.img_grade_bronze)
                        binding.gradeName.text = "Bronze 등급"
                    } else if (grade == "Sliver"){
                        binding.gradeImage.setImageResource(R.drawable.img_grade_silver)
                        binding.gradeName.text = "Silver 등급"
                    } else if (grade == "Gold"){
                        binding.gradeImage.setImageResource(R.drawable.img_grade_gold)
                        binding.gradeName.text = "Gold 등급"
                    } else if (grade == "Platinum"){
                        binding.gradeImage.setImageResource(R.drawable.img_grade_platinum)
                        binding.gradeName.text = "Platinum 등급"
                    } else if (grade == "Diamond"){
                        binding.gradeImage.setImageResource(R.drawable.img_grade_diamond)
                        binding.gradeName.text = "Diamond 등급"
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }


        return binding.root
    }

    fun gradeAndNext(totalPoint:Int): List<Any> {
        var grade = ""
        var nextGrade = ""
        var toNext = 0
        var forProgress = 0
        if (totalPoint >= 0 && totalPoint <= 499){
            grade = "Bronze"
            nextGrade = "Silver"
            toNext = 500-totalPoint
            forProgress = ((totalPoint.toDouble()/500)*100).toInt()
        } else if (totalPoint >= 500 && totalPoint <= 999){
            grade = "Silver"
            nextGrade = "Gold"
            toNext = 1000-totalPoint
            forProgress = ((totalPoint.toDouble()/1000)*100).toInt()
        } else if (totalPoint >= 1000 && totalPoint <= 1999){
            grade = "Gold"
            nextGrade = "Platinum"
            toNext = 2000-totalPoint
            forProgress = ((totalPoint.toDouble()/2000)*100).toInt()
        }else if (totalPoint >= 2000 && totalPoint <= 4999){
            grade = "Platinum"
            nextGrade = "Diamond"
            toNext = 5000-totalPoint
            forProgress = ((totalPoint.toDouble()/5000)*100).toInt()
        } else if (totalPoint >= 5000){
            grade = "Diamond"
        } else {
            Log.e("error","error")
        }
        var rst = listOf(grade,nextGrade,toNext,forProgress)

        return rst
    }
}