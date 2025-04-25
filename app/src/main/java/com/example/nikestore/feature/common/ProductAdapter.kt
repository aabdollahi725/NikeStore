package com.example.nikestore.feature.common

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nikestore.R
import com.example.nikestore.data.product.Product
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.NikeImageView
import com.sevenlearn.nikestore.common.formatPrice
import com.sevenlearn.nikestore.common.implementSpringAnimationTrait

const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class ProductAdapter(
    var viewType: Int = VIEW_TYPE_ROUND,
    val imageLoadingService: ImageLoadingService
) : Adapter<ProductAdapter.ProductViewHolder>() {

    var products = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var productOnClickListener: ProductOnClickListener? = null

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutResId =
            when (viewType) {
                VIEW_TYPE_ROUND -> R.layout.item_product_round
                VIEW_TYPE_LARGE -> R.layout.item_product_large
                VIEW_TYPE_SMALL -> R.layout.item_product_small
                else -> throw IllegalStateException("View type is not valid")

            }
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        )
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
        val productIv: NikeImageView = itemView.findViewById(R.id.favoriteProductIv)
        val favoriteBtn: ImageView = itemView.findViewById(R.id.favoriteBtn)

        fun bindProduct(product: Product) {
            titleTv.text = product.title
            previousPriceTv.text = formatPrice(product.previousPrice)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(product.price)
            imageLoadingService.load(productIv, product.image)
            favoriteBtn.setImageResource(if (product.isFavorite) R.drawable.ic_favorites_fill_20 else R.drawable.ic_favorites_20)
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productOnClickListener?.onClick(product)
            }

            favoriteBtn.setOnClickListener {
                product.isFavorite = !product.isFavorite
                notifyItemChanged(bindingAdapterPosition)
                productOnClickListener?.onFavoriteBtnClick(product)
            }
        }
    }

    interface ProductOnClickListener {
        fun onClick(product: Product)
        fun onFavoriteBtnClick(product: Product)
    }
}