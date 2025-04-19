package com.example.nikestore.feature.favorites

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.data.product.Product
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.NikeImageView
import com.sevenlearn.nikestore.common.formatPrice

class FavoriteAdapter(
    private val imageLoadingService: ImageLoadingService,
    val products: MutableList<Product>,
    private val favoriteItemEventListener: FavoriteItemEventListener
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder = FavoriteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
    )

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productIv = itemView.findViewById<NikeImageView>(R.id.favoriteProductIv)
        val productTitleTv = itemView.findViewById<TextView>(R.id.favoriteProductTitleTv)
        fun bindProduct(product: Product) {
            imageLoadingService.load(productIv, product.image)
            productTitleTv.text = product.title
            itemView.setOnClickListener {
                favoriteItemEventListener.onFavoriteItemClick(product)
            }
        }
    }

    interface FavoriteItemEventListener {
        fun onFavoriteItemClick(product: Product)
    }

    fun remove(position:Int,product: Product){
        products.remove(product)
        notifyItemRemoved(position)
    }
    fun restore(position:Int,product: Product){
        products.add(position,product)
        notifyItemInserted(position)
    }
}