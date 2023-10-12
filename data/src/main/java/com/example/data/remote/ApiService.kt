package com.example.data.remote

import com.example.domain.models.AddOrDeleteFavoriteModel
import com.example.domain.models.CartModel
import com.example.domain.models.CartModelAddToCart
import com.example.domain.models.CategoryDetailsModel
import com.example.domain.models.CategoryModel
import com.example.domain.models.EditQtyModel
import com.example.domain.models.FavoritesModel
import com.example.domain.models.HomeData
import com.example.domain.models.LogoutModel
import com.example.domain.models.RegisterModel
import com.example.domain.models.SearchModel
import com.example.domain.models.UserModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://student.valuxapps.com/api/"

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Header("lang") lang: String
    ): UserModel

    //@Headers("lang:en","Content-Type:application/json")
    @POST("register")
    suspend fun register(
        @Body model: RegisterModel,
        @Header("lang") lang: String,
    ): UserModel

//    @POST("register")
//    suspend fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//        @Field("phone") phone: String,
//        @Header("lang") lang: String,
//        @Header("Authorization") Authorization: String
//    ): UserModel

    //@Headers("Authorization:9TB3rIWQgyA88YlijtPa6JBfU4mrLE7TVBbuzYUncJW39EslLQ0fUyIDkYZvfmKVYCJ7C5")
    @GET("home")
    suspend fun getHomeData(
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): HomeData

    @FormUrlEncoded
    @POST("favorites")
    suspend fun addOrDeleteFavorite(
        @Field("product_id") id: Int,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): AddOrDeleteFavoriteModel

    @FormUrlEncoded
    @POST("products/search")
    suspend fun searchProducts(
        @Field("text") text: String,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): SearchModel

    @GET("carts")
    suspend fun getCartData(
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): CartModel

    @FormUrlEncoded
    @POST("carts")
    suspend fun addToCart(
        @Field("product_id") productId: Int,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): CartModelAddToCart

    @FormUrlEncoded
    @PUT("carts/{id}")
    suspend fun editQty(
        @Path("id") id: Int,
        @Field("quantity") qty: Int,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): EditQtyModel

    @GET("categories")
    suspend fun getCategoryData(@Header("lang") lang: String): CategoryModel

    @GET("categories/{id}")
    suspend fun getCategoryDetails(
        @Path("id") id: Int,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): CategoryDetailsModel

    @FormUrlEncoded
    @POST("logout")
    suspend fun logOut(
        @Field("fcm_token") fcmToken: String,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): LogoutModel

    @FormUrlEncoded
    @PUT("update-profile")
    suspend fun updateProfile(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): UserModel

    @GET("favorites")
    suspend fun getFavorites(
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): FavoritesModel

    @FormUrlEncoded
    @POST("change-password")
    suspend fun changePass(
        @Field("current_password") currentPass: String,
        @Field("new_password") newPass: String,
        @Header("lang") lang: String,
        @Header("Authorization") authorization: String
    ): LogoutModel
}


object A2ZApi {
    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(myHttpClient()).build()

    val a2ZApiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

fun myHttpClient(): OkHttpClient {
    val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
        .readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
    return builder.build()
}
