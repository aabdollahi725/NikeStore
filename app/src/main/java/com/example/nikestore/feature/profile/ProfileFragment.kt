package com.example.nikestore.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestore.R
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.databinding.FragmentProfileBinding
import com.example.nikestore.feature.auth.AuthActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : NikeFragment() {

    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!
    private val viewModel:ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkAuth()
    }

    private fun checkAuth(){
        if(viewModel.isLogin){
            binding.usernameTv.text=viewModel.username
            binding.authTv.text=getString(R.string.logout)
            binding.authTv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_logout,0)
            binding.authTv.setOnClickListener{
                viewModel.logout()
                showToast(getString(R.string.notifyLogout))
                checkAuth()
            }
        }
        else{
            binding.usernameTv.text=getString(R.string.guest)
            binding.authTv.text=getString(R.string.loginTitle)
            binding.authTv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_login,0)
            binding.authTv.setOnClickListener {
                startActivity(Intent(requireContext(),AuthActivity::class.java))
            }
        }
    }
}