package com.example.nikestore.services.http

import com.example.nikestore.data.Banner
import com.sevenlearn.nikestore.data.Product
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {

    @Headers("Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiNDc1ZGI2YTMzMzQ3YjkxMGY4MDZlZmU0MTE5NGZjN2RjMjQ3MmRlOWQzMGE4M2E2YTk2Yjc1Y2FkZmE2ZWIyMTU5NmE0MWRjZTEwMmIwNzMiLCJpYXQiOjE3MzczNjA4NTQuMzI4NDUsIm5iZiI6MTczNzM2MDg1NC4zMjg0NTMsImV4cCI6MTczODY1Njg1NC4yNjkwOTQsInN1YiI6IjUiLCJzY29wZXMiOltdfQ.cWaQwBwjXTfmphLjGoNNvYsLEr7KdxtHTNPfAfIqcED1k9xPBlEkHpCY0g_rlHmFVGawej_LjU8iJJcFv7KUU7bu0nEaZraASa-nfHGvKdWMV2PkiAp3Q-UIm-lVFnYU8KoE1EX-orY72DiXUiZYIKZciJejr0oOfvk0IqFS4YDoeOPDGmeCHsBK1Tzb2zeOeAsfAcWHbspbLqooKX-43QfkvyusnOVnRVj_NY_0_Zydha8tiY-rGBL_X6q5OJQ6vRWlSMGfP3DymSkpztLIbRt5rCClqm4eIzbvyY2JskBZlI8OQOwXWKKk7Mf5Qwf8mEMQ2JRG01zehetBe-e09NJPt6lmGUogKPEaBoTDJPJk2b2okXhR4NXXQ1-YeL3jGHz07lIOny8D1o9nUk0IJILlFJ2Mkxdk1w1yZOpxrMBQhccmmINvqCU3UW1g4vSvjCdkkHktfl7JUOAYKPjnc808QMmt-nYWqGDeMUc0-0h7MdHQF3dL85Q011QvJ7dZwjbJZTYMopSKv5sGyxb1dhIJ2WNnUOFuC-ZPeqaHZk-G7Dps2i_i8RsZ_rT7H42d6z22Z_qx6ECF4yAtZEXcpMeqGdfLYkWTTeQf6LsppMY0su_zSYPYbCX5OTYad2v0wieULLNxJTiLoPQ3xZLlYu8Fw8JyVexbBKXeDxFQmEc")
    @GET("product/list")
    fun getProducts(): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners():Single<List<Banner>>
}

fun createInstanceFromApiService():ApiService{

    val retrofit=Retrofit.Builder()
        .baseUrl("https://fapi.7learn.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}