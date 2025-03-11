package com.example.nikestore.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nikestore.data.banner.Banner

class BannerAdapter(fragment: Fragment,val banners:List<Banner>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return banners.size
    }

    override fun createFragment(position: Int): Fragment {
        return BannerFragment.newInstance(banners[position])
    }
}