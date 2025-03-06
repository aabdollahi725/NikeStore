package com.example.nikestore.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.data.cart.CartItem
import com.example.nikestore.data.product.Product
import com.example.nikestore.databinding.FragmentCartBinding
import com.example.nikestore.feature.auth.AuthActivity
import com.example.nikestore.feature.product.ProductDetailActivity
import com.example.nikestore.services.ImageLoadingService
import com.google.android.material.button.MaterialButton
import com.sevenlearn.nikestore.common.asyncNetWorkRequest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : NikeFragment(), CartAdapter.CartItemViewCallbacks {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModel()
    private val imageLoadingService: ImageLoadingService by inject()
    private var adapter: CartAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        viewModel.cartItemsLiveData.observe(viewLifecycleOwner) {
            adapter = CartAdapter(imageLoadingService, it as MutableList<CartItem>, this)
            binding.cartRv.adapter = adapter
            binding.cartRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner) {
            adapter?.let { cartAdapter ->
                cartAdapter.purchaseDetail = it
                cartAdapter.notifyItemChanged(cartAdapter.cartItems.size)
            }
        }

        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
            val emptyState = showEmptyState(R.layout.view_empty_state)
            if (it.mustShow) {
                emptyState?.let { view ->
                    view.findViewById<TextView>(R.id.emptyStateMessageTv).text =
                        getString(it.messageResId)
                    val ctaBtn = view.findViewById<MaterialButton>(R.id.ctaBtn)
                    val emptyStateIv = view.findViewById<ImageView>(R.id.emptyStateIv)
                    if (it.image != 0) emptyStateIv.setImageResource(it.image)
                    ctaBtn.visibility = if (it.ctaMustShow) View.VISIBLE else View.GONE

                    ctaBtn.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }
                }
            } else {
                emptyState?.findViewById<View>(R.id.emptyStateRootView)?.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRemoveBtnClick(cartItem: CartItem) {
        viewModel.removeCartItem(cartItem)
            .asyncNetWorkRequest()
            .subscribe(object : NikeCompletableObserver(viewModel.compositeDisposable) {
                override fun onComplete() {
                    adapter?.removeItem(cartItem)
                }
            })
    }

    override fun onIncreaseBtnClick(cartItem: CartItem) {
        viewModel.increaseItemCount(cartItem)
            .asyncNetWorkRequest()
            .subscribe(object : NikeCompletableObserver(viewModel.compositeDisposable) {
                override fun onComplete() {
                    adapter?.increaseOrDecreaseItemCount(cartItem)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    adapter?.setProgressBarInvisible(cartItem)
                }
            })
    }

    override fun onDecreaseBtnClick(cartItem: CartItem) {
        viewModel.decreaseItemCount(cartItem)
            .asyncNetWorkRequest()
            .subscribe(object : NikeCompletableObserver(viewModel.compositeDisposable) {
                override fun onComplete() {
                    adapter?.increaseOrDecreaseItemCount(cartItem)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    adapter?.setProgressBarInvisible(cartItem)
                }
            })
    }

    override fun onItemImageClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}