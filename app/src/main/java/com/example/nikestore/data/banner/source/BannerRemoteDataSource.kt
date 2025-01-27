package com.example.nikestore.data.banner.source

import com.example.nikestore.data.banner.Banner
import com.example.nikestore.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService) : BannerDataSource {
    override fun getAll(): Single<List<Banner>> {
        return apiService.getBanners()
    }
}