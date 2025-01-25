package com.example.nikestore.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nikestore.R
import com.example.nikestore.data.Product
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.NikeImageView
import com.sevenlearn.nikestore.common.formatPrice
import com.sevenlearn.nikestore.common.implementSpringAnimationTrait

class ProductAdapter(val imageLoadingService: ImageLoadingService) : Adapter<ProductAdapter.ProductViewHolder>() {

    var products=ArrayList<Product>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    var productOnClickListener:ProductOnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    inner class ProductViewHolder(itemView: View) : ViewHolder(itemView) {

        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val previousPriceTv: TextView = itemView.findViewById(R.id.previousTv)
        val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        val productIv: NikeImageView = itemView.findViewById(R.id.productIv)

        fun bindProduct(product: Product) {
            titleTv.text = product.title
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(product.price)
            imageLoadingService.load(productIv,product.image)
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productOnClickListener?.onClick(product)
            }
        }
    }

    interface ProductOnClickListener{
        fun onClick(product: Product)
    }
}