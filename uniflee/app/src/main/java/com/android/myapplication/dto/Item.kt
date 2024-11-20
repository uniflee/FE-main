package com.android.myapplication.dto

data class ItemDescription(
    val imageUrl : String,
    val description : String
)

data class ItemRequestDto(
    val featuredImageUrl : String,
    val name : String,
    val price : Int,
    val descriptions: ItemDescription
)

data class ItemUpdateRequest(
    val featuredImageUrl : String,
    val name : String,
    val price: Int,
    val descriptions: ItemDescription
)

data class ItemResponseDto(
    val id : Int,
    val featuredImageUrl : String,
    val designerName : String,
    val name : String,
    val price : Int
)

data class OwnItemDetailResponse(
    val id : Int,
    val featuredImageUrl : String,
    val designerName : String,
    val name : String,
    val price : Int,
    val descriptions: MutableList<ItemDescription>
)