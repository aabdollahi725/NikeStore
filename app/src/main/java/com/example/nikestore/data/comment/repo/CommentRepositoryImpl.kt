package com.example.nikestore.data.comment.repo

import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.comment.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource): CommentRepository {
    override fun getAll(productId: Int): Single<List<Comment>> {
        return commentDataSource.getAll(productId)
    }

    override fun add(title:String,content:String,productId:Int): Single<Comment> =commentDataSource.add(title,
        content,productId)
}