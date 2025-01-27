package com.example.nikestore.data.banner.source

import com.example.nikestore.data.banner.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getAll(): Single<List<Banner>>
}