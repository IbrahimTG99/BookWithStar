package com.devsinc.bws.ui.findvenue

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devsinc.bws.R

class FindVenueFragment : Fragment() {

    companion object {
        fun newInstance() = FindVenueFragment()
    }

    private lateinit var viewModel: FindVenueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_venue, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FindVenueViewModel::class.java)
        // TODO: Use the ViewModel
    }

}