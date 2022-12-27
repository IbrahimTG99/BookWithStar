package com.devsinc.bws.ui.mybookings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devsinc.bws.R

class MyBookingsFragment : Fragment() {

    companion object {
        fun newInstance() = MyBookingsFragment()
    }

    private lateinit var viewModel: MyBookingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_bookings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyBookingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}