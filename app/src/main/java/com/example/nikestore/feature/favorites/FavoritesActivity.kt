package com.example.nikestore.feature.favorites

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.SORT_NEWEST
import com.example.nikestore.databinding.ActivityFavoritesBinding
import com.example.nikestore.feature.list.ProductListActivity
import com.example.nikestore.feature.product.ProductDetailActivity
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesActivity : NikeActivity(), FavoriteAdapter.FavoriteItemEventListener {
    lateinit var binding: ActivityFavoritesBinding
    val viewModel: FavoritesViewModel by viewModel()
    lateinit var adapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.favoriteProducts.observe(this) {
            if(it.isNotEmpty()){
                adapter = FavoriteAdapter(
                    get(),
                    it as MutableList<Product>, this
                )
                binding.favoritesListRv.adapter = adapter
                binding.favoritesListRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }

        }

        binding.helpBtn.setOnClickListener {
            FavoriteGuideBottomSheetDialog().show(supportFragmentManager, null)
        }

        binding.nikeToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        viewModel.emptyStateLiveData.observe(this) {
            val emptyState = showEmptyState(R.layout.view_favorites_empty_state)
            if (it.mustShow) {
                emptyState?.let { view ->
                    view.findViewById<View>(R.id.ctaBtn).setOnClickListener {
                        startActivity(Intent(this, ProductListActivity::class.java).apply {
                            putExtra(EXTRA_KEY_DATA, SORT_NEWEST)
                        })
                    }
                }
            } else {
                emptyState?.visibility = View.GONE
            }

        }

        val itemTouchHelper =
            ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.bindingAdapterPosition
                    val product = adapter.products[position]
                    viewModel.removeFromFavorites(product)
                    adapter.remove(position, product)
                    rootView?.let {
                        val snackBar =
                            Snackbar.make(
                                it,
                                product.title + " " + getString(R.string.removedFromFavorites),
                                Snackbar.LENGTH_LONG
                            )
                        snackBar.setAction(R.string.restore) {
                            viewModel.addToFavorites(product)
                            adapter.restore(position, product)
                        }.show()
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addBackgroundColor(
                            MaterialColors.getColor(
                                recyclerView,
                                R.attr.favoriteSwipeItemBackground
                            )
                        )
                        .addActionIcon(R.drawable.ic_delete_sweep)
                        .create()
                        .decorate()
                }
            })
        itemTouchHelper.attachToRecyclerView(binding.favoritesListRv)
    }

    override fun onFavoriteItemClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteProducts()
    }
}