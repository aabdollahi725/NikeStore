package com.example.nikestore.data.comment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val author: Author
) : Parcelable
