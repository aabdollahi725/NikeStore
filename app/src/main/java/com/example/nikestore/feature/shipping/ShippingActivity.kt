package com.example.nikestore.feature.shipping

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.databinding.ActivityShippingBinding
import com.sevenlearn.nikestore.common.formatPrice
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShippingActivity : NikeActivity() {

    private val viewModel: ShippingViewModel by viewModel()
    lateinit var binding: ActivityShippingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shippingToolbarView.onBackButtonClickListener= View.OnClickListener{
            finish()
        }

        viewModel.purchaseDetailLiveData.observe(this){
            binding.purchaseDetailView.totalPriceTv.text= formatPrice(it.total_price)
            binding.purchaseDetailView.shippingCostTv.text= formatPrice(it.shipping_cost)
            binding.purchaseDetailView.payablePriceTv.text= formatPrice(it.payable_price)
        }
    }
}