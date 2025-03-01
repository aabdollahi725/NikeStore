package com.example.nikestore.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.databinding.FragmentCartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment : NikeFragment() {

    private var _binding:FragmentCartBinding?=null
    private val binding get() = _binding!!
    private val viewModel:CartViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        viewModel.cartItemsLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}