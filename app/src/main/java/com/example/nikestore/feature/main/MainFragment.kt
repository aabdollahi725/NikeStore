package com.example.nikestore.feature.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.data.Product
import com.example.nikestore.feature.product.ProductDetailActivity
import com.sevenlearn.nikestore.common.convertDpToPixel
import com.sevenlearn.nikestore.common.createBanners
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : NikeFragment(),ProductAdapter.ProductOnClickListener {

    val mainViewModel: MainViewModel by viewModel()
    lateinit var handler: Handler
    var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newestProductsRv: RecyclerView = view.findViewById(R.id.newestProductsRv)
        val popularProductsRv: RecyclerView = view.findViewById(R.id.popularProductsRv)

        newestProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        popularProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        val popularProductsAdapter: ProductAdapter = get()
        val newestProductsAdapter: ProductAdapter = get()
        newestProductsAdapter.productOnClickListener=this
        popularProductsAdapter.productOnClickListener=this

        newestProductsRv.adapter = newestProductsAdapter
        popularProductsRv.adapter = popularProductsAdapter

        handler = Handler(Looper.myLooper()!!)

        mainViewModel.newestProducts.observe(viewLifecycleOwner) {
            newestProductsAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.popularProducts.observe(viewLifecycleOwner) {
            popularProductsAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.progressBar.observe(viewLifecycleOwner) {
            if (it)
                showProgressIndicator(true)
            else
                showProgressIndicator(false)
        }


        mainViewModel.banners.observe(viewLifecycleOwner) {
            val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2_main)
            val adapter = BannerAdapter(this, createBanners())

            val runnable = object : Runnable {
                override fun run() {
                    if (currentPage == viewPager2.adapter?.itemCount?.minus(1)) {
                        currentPage = 0
                    } else {
                        currentPage++
                    }

                    viewPager2.currentItem = currentPage

                    handler.postDelayed(this, 6000)
                }

            }

            viewPager2.postDelayed(runnable, 6000)

            viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    handler.removeCallbacks(runnable)
                    currentPage = position
                    handler.postDelayed(runnable,6000)
                }
            })

            viewPager2.adapter = adapter

            val layoutParams = viewPager2.layoutParams
            layoutParams.height =
                (((viewPager2.width - convertDpToPixel(32F, requireContext())) * 173) / 328).toInt()
            viewPager2.layoutParams = layoutParams

            val dotsIndicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
            dotsIndicator.attachTo(viewPager2)
        }
    }

    override fun onClick(product: Product) {


        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }
}
