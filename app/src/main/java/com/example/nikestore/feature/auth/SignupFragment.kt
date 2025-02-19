package com.example.nikestore.feature.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.nikestore.R
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.databinding.FragmentSignupBinding
import com.sevenlearn.nikestore.common.asyncNetWorkRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : NikeFragment() {

    val viewModel: AuthViewModel by viewModel()
    private var _binding:FragmentSignupBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupLoginBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer,LoginFragment())
            }.commit()
        }

        binding.signUpBtn.setOnClickListener {
            if (binding.signupUserNameEt.length() != 0 && binding.signupPasswordEt.length() >= 6) {
                viewModel.signup(
                    binding.signupUserNameEt.text.toString(),
                    binding.signupPasswordEt.text.toString()
                )
                    .asyncNetWorkRequest()
                    .subscribe(object : NikeCompletableObserver(viewModel.compositeDisposable) {
                        override fun onComplete() {
                            requireActivity().finish()
                            showToast(getString(R.string.welcome))
                        }
                    })
            }else if(binding.signupUserNameEt.length()==0 && binding.signupPasswordEt.length()>=6){
                binding.signupUserNameEtl.error = getString(R.string.enterEmailError)
            }
            else if(binding.signupUserNameEt.length()!=0 && binding.signupPasswordEt.length()<6){
                binding.signupPasswordEtl.error= getString(R.string.passwordError)
            }
            else{
                binding.signupUserNameEtl.error = getString(R.string.enterEmailError)
                binding.signupPasswordEtl.error=getString(R.string.passwordError)
            }
        }

            binding.signupPasswordEt.doOnTextChanged { text, start, before, count ->
                if (text!!.length >5&&binding.signupPasswordEtl.error!=null) {
                    binding.signupPasswordEtl.error = null
                }
        }

        binding.signupUserNameEt.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()&&binding.signupUserNameEtl.error!=null) {
                binding.signupUserNameEtl.error = null
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}