package com.example.nikestore.feature.product.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.comment.Comment

class CommentListViewModel(savedStateHandle: SavedStateHandle) : NikeViewModel() {

    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        commentsLiveData.value = savedStateHandle[EXTRA_KEY_DATA]
    }
}