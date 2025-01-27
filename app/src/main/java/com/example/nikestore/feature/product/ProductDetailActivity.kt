package com.example.nikestore.feature.product

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.databinding.ActivityProductDetailBinding
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
import com.sevenlearn.nikestore.common.convertDpToPixel
import com.sevenlearn.nikestore.common.formatPrice
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    private val productDetailViewModel: ProductDetailViewModel by viewModel()
    private val imageLoadingService: ImageLoadingService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val commentRv=binding.commentsRv
        val commentAdapter=CommentAdapter()
        commentRv.adapter=commentAdapter
        commentRv.layoutManager=LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(binding.productIv, it.image)
            binding.toolbarTitleTv.text = it.title
            binding.titleTv.text = it.title
            binding.currentPriceTv.text = formatPrice(it.price)
            binding.previousPriceTv.text = formatPrice(it.previous_price)
            binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        productDetailViewModel.commentsLiveData.observe(this){
            commentAdapter.comments= it as ArrayList<Comment>
            if(it.size>3){
                binding.showAllCommentsBtn.visibility= View.VISIBLE
            }
            else{
                commentRv.updatePadding(bottom = convertDpToPixel(108F,this).toInt())
            }
        }

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
    }
}