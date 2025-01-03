package com.example.nikestore.feature.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : NikeActivity() {
    val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.products.observe(this){
            Timber.i("onCreate: $it")
        }
        mainViewModel.error.observe(this){
            Timber.e("onCreate: $it")
        }
    }
}