package com.example.nikestore.feature.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.databinding.ActivityProductDetailBinding
import com.example.nikestore.feature.product.comment.CommentAdapter
import com.example.nikestore.feature.product.comment.CommentListActivity
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
import com.google.android.material.snackbar.Snackbar
import com.sevenlearn.nikestore.common.convertDpToPixel
import com.sevenlearn.nikestore.common.formatPrice
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProductDetailActivity : NikeActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    private val productDetailViewModel: ProductDetailViewModel by viewModel()
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter= CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(binding.productIv, it.image)
            binding.toolbarTitleTv.text = it.title
            binding.titleTv.text = it.title
            binding.currentPriceTv.text = formatPrice(it.price)
            binding.previousPriceTv.text = formatPrice(it.previous_price)
            binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        productDetailViewModel.progressBarLiveData.observe(/* owner = */ this){
                showProgressIndicator(it)
        }

        productDetailViewModel.commentsLiveData.observe(this){
            commentAdapter.comments= it as ArrayList<Comment>
            if(it.size>3){
                binding.showAllCommentsBtn.visibility= View.VISIBLE
                binding.showAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this,CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_DATA,commentAdapter.comments)
                    })
                }
            }
            else if(it.isEmpty()){
                binding.commentsEmptyState.visibility=View.VISIBLE
            }
        }

        initViews()

    }

    private fun initViews(){

        binding.commentsRv.adapter=commentAdapter
        binding.commentsRv.layoutManager=LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        binding.observableScrollView.addScrollViewCallbacks(object :ObservableScrollViewCallbacks{
            override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                val productIvHeight=binding.productIv.height
                binding.productIv.translationY=scrollY.toFloat()/2
                binding.toolbarView.alpha=scrollY.toFloat()/productIvHeight.toFloat()
            }

            override fun onDownMotionEvent() {
                Timber.i("")
            }

            override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                Timber.i("")
            }
        })

        binding.addToCartFab.setOnClickListener {
            productDetailViewModel.addToCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeCompletableObserver(productDetailViewModel.compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.addToCart_success))
                    }
                })
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}