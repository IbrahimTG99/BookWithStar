package com.devsinc.bws.ui.findteammates

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devsinc.bws.R

class FindTeammatesFragment : Fragment() {

    companion object {
        fun newInstance() = FindTeammatesFragment()
    }

    private lateinit var viewModel: FindTeammatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_teammates, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FindTeammatesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}