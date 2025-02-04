package com.example.nikestore.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikestore.R
import io.reactivex.disposables.CompositeDisposable

abstract class NikeFragment : Fragment(), NikeView{
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context
}

abstract class NikeActivity : AppCompatActivity(), NikeView{
    override val rootView: CoordinatorLayout?
        get() {
           val viewGroup: ViewGroup = window.decorView.findViewById(android.R.id.content)
            if(viewGroup !is CoordinatorLayout){
                viewGroup.children.forEach {
                    if(it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("RootView must be instance of Coordinator Layout")
            }
            else{
                return  viewGroup
            }
        }

    override val viewContext: Context?
        get() = this
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
}

abstract class NikeViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}