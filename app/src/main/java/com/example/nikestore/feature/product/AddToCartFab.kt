package com.example.nikestore.feature.product

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.nikestore.R

class AddToCartFab(view: View) {

    private val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    private val titleTv = view.findViewById<TextView>(R.id.addToCart_titleTv)

    fun changeFabState(isActive: Boolean) {
        if (isActive) {
            titleTv.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

        } else {
            progressBar.visibility = View.GONE
            titleTv.visibility = View.VISIBLE
        }
    }
}