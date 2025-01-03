package com.example.nikestore.services.http

import com.sevenlearn.nikestore.data.Product
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("product/list")
    fun getProducts(): Single<List<Product>>
}

fun createInstanceFromApiService():ApiService{
    val retrofit=Retrofit.Builder()
        .baseUrl("https://fapi.7learn.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}