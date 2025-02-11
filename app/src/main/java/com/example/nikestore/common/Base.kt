package com.example.nikestore.common

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikestore.R
import com.example.nikestore.feature.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.contracts.contract

abstract class NikeFragment : Fragment(), NikeView {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}

abstract class NikeActivity : AppCompatActivity(), NikeView {
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup: ViewGroup = window.decorView.findViewById(android.R.id.content)
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("RootView must be instance of Coordinator Layout")
            } else {
                return viewGroup
            }
        }

    override val viewContext: Context?
        get() = this

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}

interface NikeView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?
    fun showProgressIndicator(showProgress: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && showProgress) {
                    loadingView = LayoutInflater.from(context)
                        .inflate(R.layout.view_loading, rootView, false)
                    it.addView(loadingView)
                }

                loadingView?.visibility = if (showProgress) View.VISIBLE else View.GONE

            }
        }

    }

    fun showSnackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, text, duration).show()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(nikeException: NikeException) {
        viewContext?.let {
            when (nikeException.type) {
                NikeException.Type.SIMPLE -> showSnackBar(
                    nikeException.serverMessage ?: it.getString(R.string.unknownError)
                )

                NikeException.Type.AUTH -> {
                    it.startActivity(Intent(it, AuthActivity::class.java))
                    Toast.makeText(it, nikeException.serverMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

abstract class NikeViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}