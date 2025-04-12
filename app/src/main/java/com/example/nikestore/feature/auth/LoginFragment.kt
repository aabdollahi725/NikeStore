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
import com.sevenlearn.nikestore.common.asyncRequest
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

        binding.loginSignupBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, SignupFragment())
            }.commit()
        }

        binding.loginBtn.setOnClickListener {
            if (binding.loginUserNameEt.length() != 0 && binding.loginPasswordEt.length() >= 6) {
                viewModel.login(
                    binding.loginUserNameEt.text.toString(),
                    binding.loginPasswordEt.text.toString()
                )
                    .asyncRequest()
                    .subscribe(object : NikeCompletableObserver(viewModel.compositeDisposable) {
                        override fun onComplete() {
                            requireActivity().finish()
                            showToast(getString(R.string.loginSuccess))
                        }
                    })
            } else if (binding.loginUserNameEt.length() == 0 && binding.loginPasswordEt.length() >= 6) {
                binding.loginUserNameEtl.error = getString(R.string.enterEmailError)
            } else if (binding.loginUserNameEt.length() != 0 && binding.loginPasswordEt.length() < 6) {
                binding.loginPasswordEtl.error = getString(R.string.passwordError)
            } else {
                binding.loginUserNameEtl.error = getString(R.string.enterEmailError)
                binding.loginPasswordEtl.error = getString(R.string.passwordError)
            }
        }

        binding.loginPasswordEt.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 5 && binding.loginPasswordEtl.error != null) {
                binding.loginPasswordEtl.error = null
            }
        }

        binding.loginUserNameEt.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty() && binding.loginUserNameEtl.error != null) {
                binding.loginUserNameEtl.error = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}