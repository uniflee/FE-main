package com.android.myapplication.api

import com.android.myapplication.dto.BackgroundImageUpdateRequest
import com.android.myapplication.dto.DesignerInfoResponse
import com.android.myapplication.dto.DesignerNameUpdateRequest
import com.android.myapplication.dto.ItemRequestDto
import com.android.myapplication.dto.ItemResponseDto
import com.android.myapplication.dto.Login
import com.android.myapplication.dto.MembershipResponseDto
import com.android.myapplication.dto.OrderListResponseDto
import com.android.myapplication.dto.OrderRequestDto
import com.android.myapplication.dto.OrdersResponseDto
import com.android.myapplication.dto.OwnItemDetailResponse
import com.android.myapplication.dto.PresignedUrlResponse
import com.android.myapplication.dto.ProfileImageUpdateRequest
import com.android.myapplication.dto.RecyclingRequestDto
import com.android.myapplication.dto.RecyclingResponseDto
import com.android.myapplication.dto.UserInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // google-controller
    // 아직 개발 안됨
    @POST("/api/google/write")
    suspend fun addExcelList()

    // AWS S3 이미지 업로드 및 다운로드
    @GET("/api/aws/presigned-url")
    fun getPresignedUrl(
        @Query("type") type: String
    ): PresignedUrlResponse

    // RecyclingController 재활용 관련 API - 나중에
    @GET("/api/recycling")
    suspend fun getRecyclingList(
        @Header("Authorization") Authorization: String
    ) : RecyclingResponseDto
    @POST("/api/recycling")
    suspend fun newRecycling(
        @Header("Authorization") Authorization: String,
        @Body data : RecyclingRequestDto
    ) : String
    @GET("/api/recycling/guide")
    suspend fun getRecyclingGuide(
        @Header("Authorization") Authorization: String,
        @Query("itemType") itemType : String
    ) : String

    // UserController 유저 관련 API
    @GET("/api/user/membership")
    suspend fun getMembership(
        @Header("Authorization") Authorization: String
    ): MembershipResponseDto
    @GET("/api/user")
    suspend fun getUserInfo(
        @Header("Authorization") Authorization: String
    ): UserInfoResponseDto

    // DesignerController 디자이너 정보 관리 API
    @GET("/api/designer")
    suspend fun getDesignerInfo(
        @Header("Authorization") Authorization: String
    ) : DesignerInfoResponse

    // OrdersController 주문 관련 API - 나중에
    @GET("/api/orders")
    suspend fun getOrderList(
        @Header("Authorization") Authorization: String
    ) : OrderListResponseDto
    @POST("/api/orders")
    suspend fun newOrder(
        @Header("Authorization") Authorization: String,
        @Body data : OrderRequestDto
    ) : String
    @GET("/api/orders/order")
    suspend fun getOrder(
        @Header("Authorization") Authorization: String,
        @Query("id") id : Int
    ) : OrdersResponseDto


    // ItemController 상품 관리 API
    @GET("/api/item")
    suspend fun getItemList(
        @Header("Authorization") Authorization: String
    ) : MutableList<ItemResponseDto>

    @GET("/api/item/{itemId}")
    suspend fun getItemDetail(
        @Header("Authorization") Authorization: String,
        @Path("itemId") itemId: Int
    ): OwnItemDetailResponse
}