package com.example.nikestore.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.product.Product
import com.example.nikestore.databinding.ActivityProductListBinding
import com.example.nikestore.feature.common.ProductAdapter
import com.example.nikestore.feature.common.VIEW_TYPE_LARGE
import com.example.nikestore.feature.common.VIEW_TYPE_SMALL
import com.example.nikestore.feature.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : NikeActivity(), ProductAdapter.ProductOnClickListener {
    lateinit var binding: ActivityProductListBinding
    val productListViewModel: ProductListViewModel by viewModel()
    val adapter: ProductAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.productListRv.adapter = adapter
        binding.productListRv.layoutManager = gridLayoutManager

        productListViewModel.progressBarLiveData.observe(this) {
            showProgressIndicator(it)
        }

        binding.toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        adapter.productOnClickListener = this

        binding.viewTypeChangerBtn.setOnClickListener {
            if (adapter.viewType == VIEW_TYPE_SMALL) {
                adapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                adapter.notifyDataSetChanged()
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
            } else {
                adapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                adapter.notifyDataSetChanged()
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
            }
        }

        productListViewModel.selectedSortLiveData.observe(this) {
            binding.selectedSortTv.text = getString(it)
        }

        productListViewModel.productsLiveData.observe(this) {
            adapter.products = it as ArrayList<Product>
        }

        binding.sortView.setOnClickListener {
            val dialog =
                MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
                    .setSingleChoiceItems(
                        R.array.sortTitlesArray, productListViewModel.sort
                    ) { dialog, which ->
                        productListViewModel.onSelectedSortChangedByUser(which)
                        dialog.dismiss()
                    }.setTitle(getString(R.string.sort))

            dialog.show()
        }
    }

    override fun onClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}
