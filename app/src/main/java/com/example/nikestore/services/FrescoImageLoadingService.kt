package com.example.nikestore.services

import com.example.nikestore.view.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoImageLoadingService : ImageLoadingService {

    override fun load(imageView: NikeImageView, url: String) {
        if (imageView is SimpleDraweeView) {
            imageView.setImageURI(url)
        } else {
            throw IllegalStateException("imageView must be ImageView")
        }

    }
}