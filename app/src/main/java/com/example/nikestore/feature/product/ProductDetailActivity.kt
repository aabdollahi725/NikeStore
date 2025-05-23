package com.example.nikestore.feature.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.databinding.ActivityProductDetailBinding
import com.example.nikestore.feature.product.comment.AddCommentBottomSheetDialog
import com.example.nikestore.feature.product.comment.CommentAdapter
import com.example.nikestore.feature.product.comment.CommentListActivity
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
import com.sevenlearn.nikestore.common.asyncRequest
import com.sevenlearn.nikestore.common.formatPrice
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProductDetailActivity : NikeActivity(), AddCommentBottomSheetDialog.AddCommentDialogEventListener {

    private lateinit var binding: ActivityProductDetailBinding

    private val productDetailViewModel: ProductDetailViewModel by viewModel()
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productDetailViewModel.productLiveData.observe(this) { product ->
            imageLoadingService.load(binding.productIv, product.image)
            binding.toolbarTitleTv.text = product.title
            binding.titleTv.text = product.title
            binding.currentPriceTv.text = formatPrice(product.price)
            binding.previousPriceTv.text = formatPrice(product.previousPrice)
            binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.favoriteBtn.setImageResource(if (product.isFavorite) R.drawable.ic_favorites_fill_24 else R.drawable.ic_favorites_24)

            binding.addComment.setOnClickListener {
                val addCommentBottomSheet=AddCommentBottomSheetDialog()
                addCommentBottomSheet.eventListener=this
                addCommentBottomSheet.show(supportFragmentManager,null)
            }

            binding.favoriteBtn.setOnClickListener {
                product.isFavorite = !product.isFavorite
                if(product.isFavorite){
                    productDetailViewModel.addToFavorites(product)
                }else{
                    productDetailViewModel.removeFromFavorites(product)
                }
                binding.favoriteBtn.setImageResource(if (product.isFavorite) R.drawable.ic_favorites_fill_24 else R.drawable.ic_favorites_24)
            }
        }

        productDetailViewModel.progressBarLiveData.observe(/* owner = */ this) {
            setProgressIndicator(it)
        }

        productDetailViewModel.commentsLiveData.observe(this) {
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3) {
                binding.showAllCommentsBtn.visibility = View.VISIBLE
                binding.showAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_DATA, commentAdapter.comments)
                        putExtra(EXTRA_KEY_ID,productDetailViewModel.productLiveData.value?.id)
                    })
                }
            } else if (it.isEmpty()) {
                binding.commentsEmptyState.visibility = View.VISIBLE
            }
        }

        productDetailViewModel.addToCartProgressBarLiveData.observe(this) {
            rootView?.let { it1 -> AddToCartFab(it1).changeFabState(it) }
        }

        initViews()

    }

    private fun initViews() {

        binding.commentsRv.adapter = commentAdapter
        binding.commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
            override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                val productIvHeight = binding.productIv.height
                binding.productIv.translationY = scrollY.toFloat() / 2
                binding.toolbarView.alpha = scrollY.toFloat() / productIvHeight.toFloat()
            }

            override fun onDownMotionEvent() {
                Timber.i("")
            }

            override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                Timber.i("")
            }
        })

        val addToCartFab = findViewById<View>(R.id.addToCartFab)
        addToCartFab.setOnClickListener {
            productDetailViewModel.addToCart()
                .asyncRequest()
                .subscribe(object :
                    NikeCompletableObserver(productDetailViewModel.compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.addToCart_success))
                    }
                })
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onSaveButtonClicked(title: String, content: String) {
            productDetailViewModel.addComment(title,content,productDetailViewModel.productLiveData.value?.id!!.toInt())
                .asyncRequest()
                .subscribe(object : NikeCompletableObserver(productDetailViewModel.compositeDisposable){
                    override fun onComplete() {
                        showSnackBar(getString(R.string.commentAdded))
                    }

                })
    }
}