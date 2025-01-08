package com.example.nikestore.data.repo.banner

import com.example.nikestore.data.Banner
import com.example.nikestore.data.source.banner.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerRemoteDataSource) :BannerRepository{
    override fun getBanners(): Single<List<Banner>> {
        return bannerRemoteDataSource.getBanners()
    }
}