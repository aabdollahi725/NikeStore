package com.example.nikestore.feature.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.product.Product
import com.example.nikestore.databinding.ActivityFavoritesBinding
import com.example.nikestore.feature.product.ProductDetailActivity
import com.example.nikestore.services.ImageLoadingService
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class FavoritesActivity : NikeActivity(), FavoriteAdapter.FavoriteItemEventListener {
    lateinit var binding: ActivityFavoritesBinding
    val viewModel: FavoritesViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.favoriteProducts.observe(this){
            binding.favoritesListRv.adapter= FavoriteAdapter(get(),
                it as MutableList<Product>,this)
            binding.favoritesListRv.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        }

        binding.nikeToolbar.onBackButtonClickListener= View.OnClickListener{
            finish()
        }
    }

    override fun onFavoriteItemClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onFavoriteItemLongClick(product: Product) {
        viewModel.removeFromFavorites(product)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteProducts()
    }
}