package com.example.nikestore.data.comment.source

import com.example.nikestore.data.comment.Comment
import com.example.nikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) :CommentDataSource{
    override fun getAll(productId: Int): Single<List<Comment>> {
        return apiService.getComments(productId)
    }

    override fun add(title:String,content:String,productId:Int): Single<Comment> =apiService.addComment(
        JsonObject().apply {
            addProperty("title",title)
            addProperty("content",content)
            addProperty("product_id",productId)
        })
}