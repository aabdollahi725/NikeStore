package com.example.nikestore

import android.app.Application
import com.example.nikestore.data.repo.ProductRepository
import com.example.nikestore.data.repo.ProductRepositoryImpl
import com.example.nikestore.data.source.ProductLocalDataSource
import com.example.nikestore.data.source.ProductRemoteDataSource
import com.example.nikestore.feature.main.MainViewModel
import com.example.nikestore.services.http.createInstanceFromApiService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val myModules = module {
            single {
                createInstanceFromApiService()
            }

            factory <ProductRepository>{
                ProductRepositoryImpl(ProductLocalDataSource(), ProductRemoteDataSource(get()))
            }

            viewModel {
                MainViewModel(get())
            }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }


        Timber.plant(Timber.DebugTree())
    }
}