package com.example.nikestore.feature.product

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nikestore.databinding.ActivityProductDetailBinding
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
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

        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(binding.productIv, it.image)
            binding.toolbarTitleTv.text = it.title
            binding.titleTv.text = it.title
            binding.currentPriceTv.text = formatPrice(it.price)
            binding.previousPriceTv.text = formatPrice(it.previous_price)
            binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
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