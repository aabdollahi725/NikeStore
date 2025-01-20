package com.example.nikestore.feature.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.nikestore.R
import com.example.nikestore.common.NikeFragment
import com.sevenlearn.nikestore.common.createBanners
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : NikeFragment() {

    val mainViewModel: MainViewModel by viewModel()
    lateinit var handler: Handler
    var currentPage=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = Handler(Looper.myLooper()!!)

        mainViewModel.progressBar.observe(viewLifecycleOwner) {
            if (it)
                showProgressIndicator(true)
            else
                showProgressIndicator(false)
        }


        mainViewModel.banners.observe(viewLifecycleOwner) {
            val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2_main)
            val adapter = BannerAdapter(this, createBanners())
            viewPager2.adapter = adapter

            val runnable =object :Runnable {
                override fun run() {
                    if(currentPage==viewPager2.adapter?.itemCount?.minus(1)){
                        currentPage=0
                    }
                    else{
                        currentPage++
                    }

                    viewPager2.currentItem=currentPage

                    handler.postDelayed(this,5000)                }
            }

            viewPager2.postDelayed(runnable,5000)

            val dotsIndicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
            dotsIndicator.attachTo(viewPager2)
        }
    }
}
