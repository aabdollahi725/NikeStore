package com.example.nikestore.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.nikestore.R
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.databinding.FragmentLoginBinding
import com.sevenlearn.nikestore.common.asyncNetWorkRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : NikeFragment() {

    val viewModel: AuthViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
//    get در اینجا چه کار میکنه
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            if (binding.userNameEt.length() != 0 && binding.passwordEt.length() >= 6) {
                viewModel.login(
                    binding.userNameEt.text.toString(),
                    binding.passwordEt.text.toString()
                )
                    .asyncNetWorkRequest()
                    .subscribe(object : NikeCompletableObserver(viewModel.compositeDisposable) {
                        override fun onComplete() {
                            requireActivity().finish()
                            showToast(getString(R.string.loginSuccess))
                        }
                    })
            }else if(binding.userNameEt.length()==0 && binding.passwordEt.length()>=6){
                binding.userNameEtl.error = "ایمیل را وارد کنید"
            }
            else if(binding.userNameEt.length()!=0 && binding.passwordEt.length()<6){
                binding.passwordEtl.error="رمز عبور باید بیش از 6 رقم باشد"
            }
            else{
                binding.userNameEtl.error = "ایمیل را وارد کنید"
                binding.passwordEtl.error="رمز عبور باید بیش از 6 رقم باشد"
            }
        }

        binding.userNameEt.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()&&binding.userNameEtl.error!=null) {
                binding.userNameEtl.error = null
        }

        binding.passwordEt.doOnTextChanged { text, start, before, count ->
            if (text!!.length >= 6&&binding.passwordEtl.error!=null) {
                binding.passwordEtl.error = null
            }
        }
    }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}