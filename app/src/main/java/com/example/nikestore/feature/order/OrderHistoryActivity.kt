package com.example.nikestore.feature.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.databinding.ActivityOrderHistoryBinding
import com.example.nikestore.feature.auth.AuthActivity
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryActivity : NikeActivity() {
    lateinit var binding: ActivityOrderHistoryBinding
    val viewModel: OrderHistoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        viewModel.orderHistoryItems.observe(this) {
            binding.orderHistoryRv.adapter = OrderHistoryAdapter(this, it)
            binding.orderHistoryRv.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        viewModel.emptyStateLiveData.observe(this) {
            val emptyState = showEmptyState(R.layout.view_order_history_empty_state)
            if (it.mustShow) {
                emptyState?.let { view ->
                    val ctaBtn = emptyState.findViewById<MaterialButton>(R.id.ctaBtn)
                    val emptyStateHintTV =
                        emptyState.findViewById<TextView>(R.id.emptyStateMessageTv)
                    emptyStateHintTV.text = getString(it.messageResId)
                    ctaBtn.visibility = if (it.ctaMustShow) View.VISIBLE else View.GONE
                    ctaBtn.setOnClickListener {
                        startActivity(Intent(this, AuthActivity::class.java))
                    }
                }
            }else{
                emptyState?.findViewById<View>(R.id.emptyStateRootView)?.visibility = View.GONE
            }
        }

        binding.orderHistoryToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}