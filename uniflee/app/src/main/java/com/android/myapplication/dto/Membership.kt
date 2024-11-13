package com.android.myapplication.dto

data class GradeImpact(
    val treesProtected: String,
    val energySaved: String,
    val plasticPrevented: String,
    val co2Reduced: String
)

data class MembershipResponseDto(
    val totalPoints: Int,
    val grade: String,
    val name : String,
    val gradeImpact: GradeImpact
)

data class UserInfoResponseDto(
    val id : Int,
    val name : String,
    val grade: String,
    val username :  String,
    val totalPoints: Int,
    val currentPoints : Int

)

