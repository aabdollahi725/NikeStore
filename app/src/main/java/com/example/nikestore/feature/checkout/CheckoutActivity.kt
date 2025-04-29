package com.example.nikestore.feature.checkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.databinding.ActivityCheckoutBinding
import com.example.nikestore.feature.main.MainActivity
import com.example.nikestore.feature.order.OrderHistoryActivity
import com.sevenlearn.nikestore.common.formatPrice
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : NikeActivity() {
    val viewModel: CheckoutViewModel by viewModel()
    lateinit var binding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkoutToolbar.onBackButtonClickListener= View.OnClickListener{
            finish()
        }

        binding.returnHomeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        binding.goToOrderHistoryBtn.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }

        viewModel.checkoutLiveData.observe(this) {
            binding.orderStatusTv.text = it.payment_status
            binding.priceTv.text = formatPrice(it.payable_price)
            binding.checkoutTitleTv.text = if(it.purchase_success) getString(R.string.paymentSuccess) else getString(R.string.paymentFailed)
        }
    }
}