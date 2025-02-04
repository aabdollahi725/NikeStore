package com.example.nikestore.feature.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.SORT_NEWEST
import com.example.nikestore.data.product.SORT_POPULAR
import com.example.nikestore.feature.common.ProductAdapter
import com.example.nikestore.feature.common.VIEW_TYPE_ROUND
import com.example.nikestore.feature.list.ProductListActivity
import com.example.nikestore.feature.product.ProductDetailActivity
import com.google.android.material.button.MaterialButton
import com.sevenlearn.nikestore.common.createBanners
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : NikeFragment(), ProductAdapter.ProductOnClickListener, View.OnClickListener {

    val homeViewModel: HomeViewModel by viewModel()
    lateinit var handler: Handler
    var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newestProductsRv: RecyclerView = view.findViewById(R.id.newestProductsRv)
        val popularProductsRv: RecyclerView = view.findViewById(R.id.popularProductsRv)

        newestProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        popularProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        val popularProductsAdapter: ProductAdapter = get { parametersOf(VIEW_TYPE_ROUND) }
        val newestProductsAdapter: ProductAdapter = get { parametersOf(VIEW_TYPE_ROUND) }
        newestProductsAdapter.productOnClickListener = this
        popularProductsAdapter.productOnClickListener = this

        newestProductsRv.adapter = newestProductsAdapter
        popularProductsRv.adapter = popularProductsAdapter

        handler = Handler(Looper.myLooper()!!)

        homeViewModel.newestProducts.observe(viewLifecycleOwner) {
            newestProductsAdapter.products = it as ArrayList<Product>
        }

        homeViewModel.popularProducts.observe(viewLifecycleOwner) {
            popularProductsAdapter.products = it as ArrayList<Product>
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            showProgressIndicator(it)
        }


        homeViewModel.banners.observe(viewLifecycleOwner) {
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
                    handler.postDelayed(runnable, 6000)
                }
            })

            viewPager2.adapter = adapter

            //todo bug fix this functionality
            /*
            val layoutParams = viewPager2.layoutParams
            layoutParams.height =
                (((viewPager2.width - convertDpToPixel(32F, requireContext())) * 173) / 328).toInt()
            viewPager2.layoutParams = layoutParams
*/

            val dotsIndicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
            dotsIndicator.attachTo(viewPager2)

            val showLatestProducts = view.findViewById<MaterialButton>(R.id.showLatestProducts)
            showLatestProducts.setOnClickListener(this)

            val showPopularProducts = view.findViewById<MaterialButton>(R.id.showPopularProducts)
            showPopularProducts.setOnClickListener(this)
        }
    }

    override fun onClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onClick(v: View?) {
        startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
            putExtra(
                EXTRA_KEY_DATA,
                if (v?.id == R.id.showPopularProducts) SORT_POPULAR else SORT_NEWEST
            )
        })
    }
}
