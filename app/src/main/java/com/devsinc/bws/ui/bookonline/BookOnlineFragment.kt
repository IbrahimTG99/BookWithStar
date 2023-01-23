package com.devsinc.bws.ui.bookonline

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devsinc.bws.R

class BookOnlineFragment : Fragment() {

    companion object {
        fun newInstance() = BookOnlineFragment()
    }

    private lateinit var viewModel: BookOnlineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_online, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookOnlineViewModel::class.java)
        // TODO: Use the ViewModel


    }

}