package com.devsinc.bws.ui.contactus

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentContactUsBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import com.devsinc.bws.ui.home.HomeFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BindingFragment<FragmentContactUsBinding>() {

    companion object {
        fun newInstance() = ContactUsFragment()
    }

    private val viewModel: ContactUsViewModel by viewModels()
    private lateinit var gMap: GoogleMap

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentContactUsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        (activity as AppCompatActivity).supportActionBar?.title = "Contact Us"
        viewModel.getContactInfo()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        lifecycleScope.launchWhenStarted {
            viewModel.contactInfoFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        gMap = mapFragment.awaitMap()
                        binding.tvAddress.text = resource.result.address
                        binding.tvAddress.setOnClickListener {
                            val address = resource.result.address
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
                            binding.tvAddress.context.startActivity(intent)
                        }


                        binding.tvEmail.text = resource.result.email
                        binding.tvEmail.setOnClickListener {
                            val email = resource.result.email
                            val intent =
                                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
                            binding.tvEmail.context.startActivity(intent)
                        }


                        binding.tvPhone.text = resource.result.mobile
                        binding.tvPhone.setOnClickListener {
                            val phone = resource.result.mobile
                            val intent =
                                Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
                            binding.tvPhone.context.startActivity(intent)
                        }

                        binding.tvWebsite.text = resource.result.website
                        binding.tvWebsite.setOnClickListener {
                            var website = resource.result.website
                            if (!website.startsWith("http://") && !website.startsWith("https://"))
                                website = "http://$website"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                            binding.tvWebsite.context.startActivity(intent)
                        }


                        val lat = resource.result.latitude.toDouble()
                        val long = resource.result.longitude.toDouble()
                        val name = "Clarion School"
                        val marker = MarkerOptions().title(name).position(LatLng(lat, long))
                        gMap.addMarker(marker)
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, long), 15f))
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    else -> {}
                }
            }
        }

    }
}