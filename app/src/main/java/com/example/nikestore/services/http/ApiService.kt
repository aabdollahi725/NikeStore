package com.example.nikestore.services.http

import com.example.nikestore.data.banner.Banner
import com.example.nikestore.data.cart.AddToCarTResponse
import com.example.nikestore.data.cart.CartResponse
import com.example.nikestore.data.cart.CountResponse
import com.example.nikestore.data.cart.MessageResponse
import com.example.nikestore.data.checkout.Checkout
import com.example.nikestore.data.checkout.SubmitOrderResult
import com.example.nikestore.data.user.SignupTokenResponse
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.user.TokenContainer
import com.example.nikestore.data.user.LoginTokenResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort: String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId: Int): Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject): Single<AddToCarTResponse>

    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject): Single<LoginTokenResponse>

    //    todo استاد احتمالا اشتباه از message response استفاده کردن: yes in new api
    @POST("user/register")
    fun signup(@Body jsonObject: JsonObject): Single<SignupTokenResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<LoginTokenResponse>

    @GET("cart/list")
    fun getCartItems():Single<CartResponse>

    @POST("cart/remove")
    fun removeCartItem(@Body jsonObject: JsonObject):Single<MessageResponse>

    @GET("cart/count")
    fun getCartCount():Single<CountResponse>

    @POST("cart/changeCount")
    fun changeCartItemCount(@Body jsonObject: JsonObject):Single<AddToCarTResponse>

    @POST("order/submit")
    fun submitOrder(@Body jsonObject: JsonObject): Single<SubmitOrderResult>

    @GET("order/checkout")
    fun checkoutOrder(@Query("order_id") orderId:Int): Single<Checkout>
}

fun createInstanceFromApiService(): ApiService {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequest = oldRequest.newBuilder()
            if (TokenContainer.token != null)
                newRequest.addHeader("Authorization", "Bearer ${TokenContainer.token}")

            newRequest.addHeader("Accept", "application/json")
            newRequest.method(oldRequest.method, oldRequest.body)
            return@addInterceptor it.proceed(newRequest.build())
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .authenticator(NikeAuthenticator())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://fapi.7learn.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)
}