package com.devsinc.bws.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentCartBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import com.devsinc.bws.utils.NetworkConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BindingFragment<FragmentCartBinding>() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCartBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Cart"

        arguments?.getString("slotIds")?.let { viewModel.getCart(it, NetworkConstants.CUSTOMERID) }

        lifecycleScope.launchWhenStarted {
            viewModel.cartFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val cartAdapterCartItems = RecyclerAdapterCartItems(resource.result.toMutableList())
                        binding.rvCart.adapter = cartAdapterCartItems
                        cartAdapterCartItems.itemClickListener = {
                            viewModel.changeCartStatus(it.tslot_id.toString(), 0)
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            resource.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        Log.d("CartFragment", "Loading")
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cartStatusFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), resource.result?.datum, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Log.d("CartFragment", "Loading")
                    }
                    else -> {}
                }
            }
        }
    }
}