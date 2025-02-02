package com.example.nikestore

import android.app.Application
import com.example.nikestore.data.banner.repo.BannerRepository
import com.example.nikestore.data.banner.repo.BannerRepositoryImpl
import com.example.nikestore.data.banner.source.BannerRemoteDataSource
import com.example.nikestore.data.comment.repo.CommentRepository
import com.example.nikestore.data.comment.repo.CommentRepositoryImpl
import com.example.nikestore.data.comment.source.CommentRemoteDataSource
import com.example.nikestore.data.product.repo.ProductRepository
import com.example.nikestore.data.product.repo.ProductRepositoryImpl
import com.example.nikestore.data.product.source.ProductLocalDataSource
import com.example.nikestore.data.product.source.ProductRemoteDataSource
import com.example.nikestore.feature.list.ProductListViewModel
import com.example.nikestore.feature.home.HomeViewModel
import com.example.nikestore.feature.common.ProductAdapter
import com.example.nikestore.feature.product.ProductDetailViewModel
import com.example.nikestore.feature.product.comment.CommentListViewModel
import com.example.nikestore.services.FrescoImageLoadingService
import com.example.nikestore.services.ImageLoadingService
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

            factory <ProductAdapter> { (viewType:Int)->
                ProductAdapter(viewType,get())
            }

            factory <CommentRepository>{
                CommentRepositoryImpl(CommentRemoteDataSource(get()))
            }
            viewModel {
                HomeViewModel(get(), get())
            }

            viewModel{
                ProductDetailViewModel(get(),get())
            }

            viewModel{
                CommentListViewModel(get())
            }

            viewModel{
                ProductListViewModel(get(),get())
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