package com.example.nikestore.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
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

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.layoutDirection=View.LAYOUT_DIRECTION_RTL
        super.onCreate(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}

interface NikeView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?
    fun setProgressIndicator(showProgress: Boolean) {
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

    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
        viewContext?.let {
            Toast.makeText(it, text, duration).show()
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
                    nikeException.serverMessage?.let { it1 -> showToast(it1, Toast.LENGTH_SHORT) }
                }
            }
        }
    }

    fun showEmptyState(layoutResId:Int):View?{
        rootView?.let {rootView->
            viewContext?.let {
                var emptyState =rootView.findViewById<View>(R.id.emptyStateRootView)
                if(emptyState==null){
                    emptyState=LayoutInflater.from(it).inflate(R.layout.view_cart_empty_state,rootView,false)
                    rootView.addView(emptyState)
                }
                emptyState.visibility=View.VISIBLE
                return emptyState
            }
        }

        return null
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