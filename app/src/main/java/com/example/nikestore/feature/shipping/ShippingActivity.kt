package com.example.nikestore.feature.shipping

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.data.checkout.SubmitOrderResult
import com.example.nikestore.databinding.ActivityShippingBinding
import com.example.nikestore.feature.checkout.CheckoutActivity
import com.google.android.material.color.MaterialColors
import com.sevenlearn.nikestore.common.asyncNetWorkRequest
import com.sevenlearn.nikestore.common.formatPrice
import com.sevenlearn.nikestore.common.openUrlInCustomTab
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShippingActivity : NikeActivity() {

    private val viewModel: ShippingViewModel by viewModel()
    lateinit var binding: ActivityShippingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shippingToolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        viewModel.purchaseDetailLiveData.observe(this) {
            binding.purchaseDetailView.totalPriceTv.text = formatPrice(it.total_price)
            binding.purchaseDetailView.shippingCostTv.text = formatPrice(it.shipping_cost)
            binding.purchaseDetailView.payablePriceTv.text = formatPrice(it.payable_price)
        }

        val onClickListener=View.OnClickListener{
            if(binding.firstNameEt.length()!=0&&binding.lastNameEt.length()!=0&&binding.postalCodeEt.length()==10&&binding.mobileEt.length()==11&&binding.addressEt.length()>20&&binding.addressEt.length()<=50){
                viewModel.submitOrder(
                    binding.firstNameEt.text.toString(),
                    binding.lastNameEt.text.toString(),
                    binding.postalCodeEt.text.toString(),
                    binding.mobileEt.text.toString(),
                    binding.addressEt.text.toString(),
                    if(it.id==R.id.codBtn) PAYMENT_METHOD_COD else PAYMENT_METHOD_ONLINE
                ).asyncNetWorkRequest()
                    .subscribe(object :
                        NikeSingleObserver<SubmitOrderResult>(viewModel.compositeDisposable) {
                        override fun onSuccess(t: SubmitOrderResult) {
                            if(t.bank_gateway_url.isNotEmpty()){
                                openUrlInCustomTab(this@ShippingActivity,t.bank_gateway_url)
                            }else{
                                startActivity(Intent(this@ShippingActivity,CheckoutActivity::class.java).apply {
                                    putExtra(EXTRA_KEY_ID,t.order_id)
                                })
                            }

                            finish()
                        }
                    })
            }else if(binding.firstNameEt.length()==0){
                binding.firstNameEtl.error=getString(R.string.enterFirstName)
            }else if(binding.lastNameEt.length()==0){
                binding.lastNameEtl.error=getString(R.string.enterLastName)
            }else if(binding.postalCodeEt.length()!=10){
                binding.postalCodeEtl.error=getString(R.string.postalCodeError)
            }else if(binding.mobileEt.length()!=11){
                binding.mobileEtl.error=getString(R.string.mobileError)
            }else if(binding.addressEt.length()>20||binding.addressEt.length()<=50){
                binding.addressEtl.error=getString(R.string.addressRequirement)
            }

        }
        binding.onlinePaymentBtn.setOnClickListener(onClickListener)

        binding.codBtn.setOnClickListener(onClickListener)
    }
}