package com.example.nikestore.feature.main

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.data.Banner
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.NikeImageView
import org.koin.android.ext.android.inject

class BannerFragment : Fragment() {

    private val imageLoadingService: ImageLoadingService by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val imageView =
            inflater.inflate(R.layout.fragment_banner, container, false) as NikeImageView
        requireArguments().parcelable<Banner>(EXTRA_KEY_DATA)
            ?.let {
                imageLoadingService.load(imageView, it.image)
            }

        return imageView
    }

    companion object {
        @JvmStatic
        fun newInstance(banner: Banner) =
            BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA, banner)
                }
            }
    }

    private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
}