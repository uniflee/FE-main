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

data class ImageAnalyzeResponse(
    val predict : String
)

data class RecyclingStrategyResponse(
    val point: Int,
    val co2 : Int,
    val disposalInstructions1 : String,
    val disposalInstructions2 : String,
    val disposalInstructions3 : String
)