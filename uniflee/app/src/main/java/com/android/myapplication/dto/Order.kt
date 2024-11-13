package com.android.myapplication.dto

data class OrdersResponseDto(
    val id : Int,
    val name : String,
    val designerName : String,
    val featuredImageUrl : String?,
    val count : Int,
    val point : Int,
    val totalPoint : Int
)

data class OrderListResponseDto(
    val currentPoint : Int,
    val name : String,
    val ordersResponseDtoList : List<OrdersResponseDto>
)

data class OrderRequestDto(
    val itemId : Int,
    val count : Int
)