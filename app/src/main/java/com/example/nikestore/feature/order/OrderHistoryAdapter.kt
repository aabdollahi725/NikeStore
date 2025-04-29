package com.example.nikestore.feature.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.data.order.OrderHistoryItem
import com.example.nikestore.view.NikeImageView
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.sevenlearn.nikestore.common.convertDpToPixel
import com.sevenlearn.nikestore.common.formatPrice


class OrderHistoryAdapter(private val context: Context,private val orderHistoryItems:List<OrderHistoryItem>) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {
    val layoutParams: LinearLayout.LayoutParams
    val roundingParams:RoundingParams
    init {
        val size=convertDpToPixel(90F,context).toInt()
        val margin=convertDpToPixel(8F,context).toInt()
        layoutParams=LinearLayout.LayoutParams(size,size)
        layoutParams.marginStart=margin
        layoutParams.marginEnd=margin
        roundingParams = RoundingParams.fromCornersRadius(convertDpToPixel(8F,context))
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_history,parent,false))


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(orderHistoryItems[position])
    }

    override fun getItemCount(): Int =orderHistoryItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTv: TextView = itemView.findViewById(R.id.orderIdTv)
        val orderAmountTv: TextView = itemView.findViewById(R.id.orderAmountTv)
        val orderProductsLl: LinearLayout = itemView.findViewById(R.id.orderProductsLl)
        fun bind(orderHistoryItem: OrderHistoryItem) {
            orderIdTv.text = orderHistoryItem.id.toString()
            orderAmountTv.text = formatPrice(orderHistoryItem.payable)
            orderProductsLl.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView= NikeImageView(context)
                imageView.layoutParams=layoutParams
                imageView.setImageURI(it.product.image)
                imageView.setHierarchy(
                    GenericDraweeHierarchyBuilder(context.resources)
                        .setRoundingParams(roundingParams)
                        .build()
                )
                orderProductsLl.addView(imageView)
            }
        }
    }
}