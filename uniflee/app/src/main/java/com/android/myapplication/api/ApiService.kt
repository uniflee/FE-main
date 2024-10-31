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
import com.android.myapplication.dto.ProfileImageUpdateRequest
import com.android.myapplication.dto.RecyclingRequestDto
import com.android.myapplication.dto.RecyclingResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // default
    @POST("/login")
    suspend fun designerLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ) : DesignerInfoResponse
    @GET("/oauth2/authorization/kookmin")
    suspend fun userLogin(): Login
    @GET("/oauth2/authorization/kookmin")
    suspend fun getLoginUrl(): retrofit2.Response<Unit>
    // google-controller
    // 아직 개발 안됨
    @POST("/api/google/write")
    suspend fun addExcelList()

    // AWS S3 이미지 업로드 및 다운로드
    @GET("/api/aws")
    suspend fun downloadImage(
        @Query("name") name : String // s3 이미지 위치
    ) : String
    @POST("/api/aws")
    suspend fun uploadImage(
        @Query("type") type : String
    ) : String

    // RecyclingController 재활용 관련 API
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

    // DesignerController 디자이너 정보 관리 API
    @PATCH("/api/designer/profileImage")
    suspend fun modifyImage(
        @Header("Authorization") Authorization: String,
        @Body profileImageUrl : ProfileImageUpdateRequest
    ) : String
    @PATCH("/api/designer/name")
    suspend fun modifyName(
        @Header("Authorization") Authorization: String,
        @Body name : DesignerNameUpdateRequest
    ) : String
    @PATCH("/api/designer/backgroundImage")
    suspend fun modifyBackgroundImage(
        @Header("Authorization") Authorization: String,
        @Body backgroundImage : BackgroundImageUpdateRequest
    )
    @GET("/api/designer")
    suspend fun getDesignerInfo(
        @Header("Authorization") Authorization: String
    ) : DesignerInfoResponse

    // OrdersController 주문 관련 API
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
    ) : ItemResponseDto
    @POST("/api/item")
    suspend fun newItem(
        @Header("Authorization") Authorization: String,
        @Body data : ItemRequestDto
    ) : String
    @DELETE("/api/item")
    suspend fun deleteItem(
        @Header("Authorization") Authorization: String
    ) : String
    @PATCH("/api/item")
    suspend fun modifyItem(
        @Header("Authorization") Authorization: String,
        @Path("itemId") itemId : Int
    )
}