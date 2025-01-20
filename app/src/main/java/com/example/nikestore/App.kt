package com.example.nikestore

import android.app.Application
import com.example.nikestore.data.repo.banner.BannerRepository
import com.example.nikestore.data.repo.banner.BannerRepositoryImpl
import com.example.nikestore.data.repo.product.ProductRepository
import com.example.nikestore.data.repo.product.ProductRepositoryImpl
import com.example.nikestore.data.source.banner.BannerRemoteDataSource
import com.example.nikestore.data.source.product.ProductLocalDataSource
import com.example.nikestore.data.source.product.ProductRemoteDataSource
import com.example.nikestore.feature.main.MainViewModel
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.services.FrescoImageLoadingService
import com.example.nikestore.services.http.createInstanceFromApiService
import com.facebook.drawee.backends.pipeline.Fresco
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

            factory<ProductRepository> {
                ProductRepositoryImpl(ProductLocalDataSource(), ProductRemoteDataSource(get()))
            }

            factory<BannerRepository> {
                BannerRepositoryImpl(BannerRemoteDataSource(get()))
            }

            single<ImageLoadingService> {
                FrescoImageLoadingService()
            }

            viewModel {
                MainViewModel(get(), get())
            }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

//        initialize Fresco image loading library
        Fresco.initialize(this)

        Timber.plant(Timber.DebugTree())
    }
}