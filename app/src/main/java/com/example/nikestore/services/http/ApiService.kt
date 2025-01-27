package com.example.nikestore.services.http

import com.example.nikestore.data.banner.Banner
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.product.Product
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort:String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners():Single<List<Banner>>

    @GET("/comment/list")
    fun getComments(@Query("product_id")productId:Int):Single<List<Comment>>

}

fun createInstanceFromApiService():ApiService{

    val retrofit=Retrofit.Builder()
        .baseUrl("https://fapi.7learn.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}