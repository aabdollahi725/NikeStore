package com.example.nikestore.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.databinding.ActivityCommentListBinding
import com.sevenlearn.nikestore.common.asyncRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentListActivity : NikeActivity(), AddCommentBottomSheetDialog.AddCommentDialogEventListener {
    lateinit var binding: ActivityCommentListBinding
    val commentListViewModel: CommentListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCommentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val commentAdapter = CommentAdapter(true)
        binding.commentsRv.adapter = commentAdapter
        binding.commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        commentListViewModel.commentsLiveData.observe(this) {
            commentAdapter.comments = it as ArrayList<Comment>
        }

        binding.addCommentBtn.setOnClickListener {
            val addCommentBottomSheet=AddCommentBottomSheetDialog()
            addCommentBottomSheet.eventListener=this
            addCommentBottomSheet.show(supportFragmentManager,null)
        }

    }

    override fun onSaveButtonClicked(title: String,content:String) {
        commentListViewModel.addComment(title,content, commentListViewModel.productId.value!!.toInt())
            .asyncRequest()
            .subscribe(object : NikeCompletableObserver(commentListViewModel.compositeDisposable){
                override fun onComplete() {
                    showSnackBar(getString(R.string.commentAdded))
                }
            })
    }
}