package com.example.nikestore.feature.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nikestore.R
import com.example.nikestore.data.cart.CartItem
import com.example.nikestore.data.cart.PurchaseDetail
import com.example.nikestore.data.product.Product
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.NikeImageView
import com.sevenlearn.nikestore.common.formatPrice

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAIL = 1

class CartAdapter(
    val imageLoadingService: ImageLoadingService,
    val cartItems: MutableList<CartItem>,
    val callbacks: CartItemViewCallbacks
) : Adapter<ViewHolder>() {

    var purchaseDetail: PurchaseDetail? = null

    //    todo یکی اینکه چرا برای قیمت قبلی صفر می امد و چرا نوتیفای کال نشد در زمان تغییرات ویو مثلا ویسیبل کردن پروگرس بار
    inner class CartViewHolder(itemView: View) : ViewHolder(itemView) {
        val productImageIv: NikeImageView = itemView.findViewById(R.id.productImageIv)
        val productTitleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        val productPreviousPriceTv: TextView = itemView.findViewById(R.id.productPreviousPriceTv)
        val productPriceTv: TextView = itemView.findViewById(R.id.productPriceTv)
        val productCountTv: TextView = itemView.findViewById(R.id.productCountTv)
        val removeItemBtn: TextView = itemView.findViewById(R.id.removeItemBtn)
        val increaseBtn: ImageView = itemView.findViewById(R.id.increaseBtn)
        val decreaseBtn: ImageView = itemView.findViewById(R.id.decreaseBtn)
        val changeCountProgressBar: ProgressBar = itemView.findViewById(R.id.changeCountProgressBar)

        fun bindCartItem(cartItem: CartItem) {
            imageLoadingService.load(productImageIv, cartItem.product.image)
            productTitleTv.text = cartItem.product.title
            productPriceTv.text = formatPrice(cartItem.product.price)
            productPreviousPriceTv.text =
                formatPrice(cartItem.product.price + cartItem.product.discount)
            productPreviousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            productCountTv.text = cartItem.count.toString()

            changeCountProgressBar.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE


                if (!cartItem.changeCountProgressBarIsVisible)
                    productCountTv.visibility = View.VISIBLE

            removeItemBtn.setOnClickListener {
                callbacks.onRemoveBtnClick(cartItem)
            }

            increaseBtn.setOnClickListener {
                if (cartItem.count < 5) {
                    cartItem.changeCountProgressBarIsVisible = true
                    productCountTv.visibility = View.INVISIBLE
                    changeCountProgressBar.visibility = View.VISIBLE
                    callbacks.onIncreaseBtnClick(cartItem)
                }
            }

            decreaseBtn.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarIsVisible = true
                    productCountTv.visibility = View.INVISIBLE
                    changeCountProgressBar.visibility = View.VISIBLE
                    callbacks.onDecreaseBtnClick(cartItem)
                }
            }

            productImageIv.setOnClickListener {
                callbacks.onItemImageClick(cartItem.product)
            }
        }
    }

    class PurchaseDetailViewHolder(itemView: View) : ViewHolder(itemView) {
        val totalPriceTv: TextView = itemView.findViewById(R.id.totalPriceTv)
        val shippingCostTv: TextView = itemView.findViewById(R.id.shippingCostTv)
        val payablePriceTv: TextView = itemView.findViewById(R.id.payablePriceTv)

        fun bind(totalPrice: Int, shippingCost: Int, payablePrice: Int) {
            totalPriceTv.text = formatPrice(totalPrice)
            shippingCostTv.text = formatPrice(shippingCost)
            payablePriceTv.text = formatPrice(payablePrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_CART_ITEM)
            CartViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
            )
        else
            PurchaseDetailViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_purchase_detail, parent, false)
            )
    }

    override fun getItemCount(): Int {
        return cartItems.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CartViewHolder)
            holder.bindCartItem(cartItems[position])
        else if (holder is PurchaseDetailViewHolder) {
            purchaseDetail?.let {
                holder.bind(it.total_price, it.shipping_cost, it.payable_price)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == cartItems.size) VIEW_TYPE_PURCHASE_DETAIL else VIEW_TYPE_CART_ITEM

    fun removeItem(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index != -1){
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseOrDecreaseItemCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if(index!=-1){
            cartItems[index].changeCountProgressBarIsVisible=false
            cartItems[index] = cartItem
            notifyItemChanged(index)
        }
    }

    fun setProgressBarInvisible(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index != -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    interface CartItemViewCallbacks {
        fun onRemoveBtnClick(cartItem: CartItem)
        fun onIncreaseBtnClick(cartItem: CartItem)
        fun onDecreaseBtnClick(cartItem: CartItem)
        fun onItemImageClick(product: Product)
    }
}