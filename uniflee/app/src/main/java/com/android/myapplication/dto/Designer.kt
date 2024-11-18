package com.android.myapplication.dto

data class DesignerInfoResponse(
    val name : String,
    val profileImageUrl	: String,
    val backgroundImageUrl : String
)

data class DesignerNameUpdateRequest(
    val name : String
)

data class ProfileImageUpdateRequest(
    val profileImageUrl: String
)

data class BackgroundImageUpdateRequest(
    val backgroundImage: String
)