package com.example.nikestore

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.nikestore.data.banner.repo.BannerRepository
import com.example.nikestore.data.banner.repo.BannerRepositoryImpl
import com.example.nikestore.data.banner.source.BannerRemoteDataSource
import com.example.nikestore.data.cart.repo.CartRepo
import com.example.nikestore.data.cart.repo.CartRepoImpl
import com.example.nikestore.data.cart.source.CartRemoteDataSource
import com.example.nikestore.data.checkout.repo.OrderRepo
import com.example.nikestore.data.checkout.repo.OrderRepoImpl
import com.example.nikestore.data.checkout.source.OrderRemoteDataSource
import com.example.nikestore.data.comment.repo.CommentRepository
import com.example.nikestore.data.comment.repo.CommentRepositoryImpl
import com.example.nikestore.data.comment.source.CommentRemoteDataSource
import com.example.nikestore.data.db.AppDataBase
import com.example.nikestore.data.product.repo.ProductRepository
import com.example.nikestore.data.product.repo.ProductRepositoryImpl
import com.example.nikestore.data.product.source.ProductRemoteDataSource
import com.example.nikestore.data.user.repo.UserRepo
import com.example.nikestore.data.user.repo.UserRepoImpl
import com.example.nikestore.data.user.source.UserLocalDataSource
import com.example.nikestore.data.user.source.UserRemoteDataSource
import com.example.nikestore.feature.auth.AuthViewModel
import com.example.nikestore.feature.cart.CartViewModel
import com.example.nikestore.feature.checkout.CheckoutViewModel
import com.example.nikestore.feature.common.ProductAdapter
import com.example.nikestore.feature.favorites.FavoritesViewModel
import com.example.nikestore.feature.home.HomeViewModel
import com.example.nikestore.feature.list.ProductListViewModel
import com.example.nikestore.feature.main.MainViewModel
import com.example.nikestore.feature.product.ProductDetailViewModel
import com.example.nikestore.feature.product.comment.CommentListViewModel
import com.example.nikestore.feature.profile.ProfileViewModel
import com.example.nikestore.feature.settings.SettingsViewModel
import com.example.nikestore.feature.shipping.ShippingViewModel
import com.example.nikestore.services.FrescoImageLoadingService
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.services.http.createInstanceFromApiService
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
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
                ProductRepositoryImpl(get<AppDataBase>().productDao(), ProductRemoteDataSource(get()))
            }

            factory<BannerRepository> {
                BannerRepositoryImpl(BannerRemoteDataSource(get()))
            }

            single<ImageLoadingService> {
                FrescoImageLoadingService()
            }

            single {
                Room.databaseBuilder(this@App, AppDataBase::class.java,"db_app").build()
            }

            factory <ProductAdapter> { (viewType:Int)->
                ProductAdapter(viewType,get())
            }

            factory <CommentRepository>{
                CommentRepositoryImpl(CommentRemoteDataSource(get()))
            }

            factory<CartRepo>{
                CartRepoImpl(CartRemoteDataSource(get()))
            }

            single <SharedPreferences>{
                this@App.getSharedPreferences("app_settings", MODE_PRIVATE)
            }

            single<UserRepo>{
                UserRepoImpl(UserRemoteDataSource(get()), UserLocalDataSource(get()))
            }

            single<OrderRepo>{
                OrderRepoImpl(OrderRemoteDataSource(get()))
            }

            viewModel {
                HomeViewModel(get(), get())
            }

            viewModel {
                SettingsViewModel(get())
            }

            viewModel {
                ShippingViewModel(get(),get())
            }

            viewModel {
                CheckoutViewModel(get(),get())
            }

            viewModel {
                FavoritesViewModel(get())
            }

            viewModel {
                MainViewModel(get(),get())
            }

            viewModel{
                ProductDetailViewModel(get(),get(),get(),get())
            }

            viewModel{
                CommentListViewModel(get())
            }

            viewModel{
                ProfileViewModel(get())
            }

            viewModel{
                ProductListViewModel(get(),get())
            }

            viewModel {
                AuthViewModel(get())
            }

            viewModel {
                CartViewModel(get())
            }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

//        initialize Fresco image loading library
        Fresco.initialize(this)

        Timber.plant(Timber.DebugTree())

        val userRepo:UserRepo=get()
        userRepo.loadToken()
    }
}