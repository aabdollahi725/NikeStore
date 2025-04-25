package com.example.nikestore.feature.product.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.comment.repo.CommentRepository
import io.reactivex.Completable

class CommentListViewModel(
    savedStateHandle: SavedStateHandle,
    private val commentRepository: CommentRepository
) : NikeViewModel() {

    val commentsLiveData = MutableLiveData<List<Comment>>()
    val productId= MutableLiveData<Int>()

    init {
        productId.value = savedStateHandle[EXTRA_KEY_ID]
        commentsLiveData.value = savedStateHandle[EXTRA_KEY_DATA]
    }

    fun addComment(title: String, content: String, productId: Int): Completable {
        return commentRepository.add(title, content, productId).ignoreElement()
    }
}