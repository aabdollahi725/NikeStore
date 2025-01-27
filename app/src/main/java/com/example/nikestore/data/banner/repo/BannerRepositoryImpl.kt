package com.example.nikestore.data.banner.repo

import com.example.nikestore.data.banner.Banner
import com.example.nikestore.data.banner.source.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerRemoteDataSource) : BannerRepository {
    override fun getAll(): Single<List<Banner>> {
        return bannerRemoteDataSource.getAll()
    }
}