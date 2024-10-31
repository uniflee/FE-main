package com.android.myapplication.api

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    // default
    @POST("/login")
    suspend fun designerLogin()
    @GET("/oauth2/authorization/kookmin")
    suspend fun userLogin()

    // google-controller
    @POST("/api/google/write")
    suspend fun addExcelList()

    // AWS S3 이미지 업로드 및 다운로드
    @GET("/api/aws")
    suspend fun downloadImage()
    @POST("/api/aws")
    suspend fun uploadImage()

    // RecyclingController 재활용 관련 API
    @GET("/api/recycling")
    suspend fun getRecyclingList()
    @POST("/api/recycling")
    suspend fun newRecycling()
    @GET("/api/recycling/guide")
    suspend fun getRecyclingGuide()

    // UserController 유저 관련 API
    @GET("/api/user/membership")
    suspend fun getMembership(@Header("Authorization") Authorization: String): String

    // DesignerController 디자이너 정보 관리 API
    @PATCH("/api/designer/profileImage")
    suspend fun modifyImage()
    @PATCH("/api/designer/name")
    suspend fun modifyName()
    @PATCH("/api/designer/backgroundImage")
    suspend fun modifyBackgroundImage()
    @GET("/api/designer")
    suspend fun getDesignerInfo()

    // OrdersController 주문 관련 API
    @GET("/api/orders")
    suspend fun getOrderList()
    @POST("/api/orders")
    suspend fun newOrder()
    @GET("/api/orders/order")
    suspend fun getOrder()


    // ItemController 상품 관리 API
    @GET("/api/item")
    suspend fun getItemList()
    @POST("/api/item")
    suspend fun newItem()
    @DELETE("/api/item")
    suspend fun deleteItem()
    @PATCH("/api/item")
    suspend fun modifyItem()
}