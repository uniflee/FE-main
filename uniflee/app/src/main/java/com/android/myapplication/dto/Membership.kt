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
    val gradeImpact: GradeImpact
)

