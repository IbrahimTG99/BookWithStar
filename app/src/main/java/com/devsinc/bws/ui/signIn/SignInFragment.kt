package com.devsinc.bws.ui.signIn

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentSignInBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import com.devsinc.bws.ui.forgotPassword.ForgotPasswordFragment
import com.devsinc.bws.utils.Constants
import com.devsinc.bws.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class SignInFragment : BindingFragment<FragmentSignInBinding>() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private val viewModel: AuthViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSignInBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.tvForgotPassword.setOnClickListener {
            // pop up forgot password modal bottom sheet
            val forgotPassBottomSheet = ForgotPasswordFragment()
            forgotPassBottomSheet.show(parentFragmentManager, ForgotPasswordFragment.TAG)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.etPassword.text.toString()
            binding.tilEmail.error = null
            binding.etPassword.error = null

            if (email.isEmpty()) {
                binding.tilEmail.error = "Email is required"
                binding.tilEmail.requestFocus()
                // open keyboard
                Constants.openKeyboard(binding.etEmail, requireContext())
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password is required"
                binding.etPassword.requestFocus()
                // open keyboard
                Constants.openKeyboard(binding.etPassword, requireContext())
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginFlow.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // navigate to home
                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                        Toast.makeText(requireContext(), result.result.toString(), Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        // show loading
                    }
                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }
}