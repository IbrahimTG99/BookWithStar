package com.devsinc.bws.ui.stadiumdetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devsinc.bws.R

class StadiumDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = StadiumDetailsFragment()
    }

    private lateinit var viewModel: StadiumDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stadium_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StadiumDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}