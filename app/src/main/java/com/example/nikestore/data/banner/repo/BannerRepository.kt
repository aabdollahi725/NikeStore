package com.example.nikestore.data.banner.repo

import com.example.nikestore.data.banner.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getAll(): Single<List<Banner>>
}