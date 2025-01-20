package com.example.nikestore.services

import com.example.nikestore.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView: NikeImageView,url:String)
}