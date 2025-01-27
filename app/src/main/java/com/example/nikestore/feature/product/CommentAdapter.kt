package com.example.nikestore.feature.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nikestore.R
import com.example.nikestore.data.comment.Comment

class CommentAdapter : Adapter<CommentAdapter.CommentViewHolder>() {

    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if(comments.size>3) 3 else comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }

    inner class CommentViewHolder(itemView: View) : ViewHolder(itemView) {

        val titleTv = itemView.findViewById<TextView>(R.id.commentTitleTv)
        val dateTv = itemView.findViewById<TextView>(R.id.commentDateTv)
        val authorTv = itemView.findViewById<TextView>(R.id.commentAuthorTv)
        val contentTv = itemView.findViewById<TextView>(R.id.commentContentTv)

        fun bindComment(comment: Comment) {
            contentTv.text = comment.content
            titleTv.text = comment.title
            dateTv.text = comment.date
            authorTv.text = comment.author.email
        }
    }
}