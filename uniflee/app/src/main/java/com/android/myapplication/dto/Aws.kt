package com.android.myapplication.dto

data class PresignedUrlResponse(
    val preSignedUrl: String,
    val resourceUrl: String
)