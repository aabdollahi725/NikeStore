package com.example.nikestore.data.comment.repo

import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.product.Product
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId:Int): Single<List<Comment>>

    fun add(title:String,content:String,productId:Int):Single<Comment>
}