package com.android.myapplication.dto

data class RecyclingResponseDto (
    val itemType : String,
    val count : Int,
    val point : Int
)

data class RecyclingRequestDto (
    val itemType : String,
    val count : Int,
)