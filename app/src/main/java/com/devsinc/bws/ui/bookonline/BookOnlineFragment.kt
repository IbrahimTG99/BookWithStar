package com.devsinc.bws.ui.bookonline

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.MainActivity
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentBookOnlineBinding
import com.devsinc.bws.model.Court
import com.devsinc.bws.model.Venue
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import com.devsinc.bws.ui.findvenue.RecyclerAdapterVenues
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

@AndroidEntryPoint
class BookOnlineFragment : BindingFragment<FragmentBookOnlineBinding>() {

    companion object {
        fun newInstance() = BookOnlineFragment()
    }

    private var dropDownOptions: HashMap<String, List<HashMap<String, List<String>>>> = hashMapOf()
    private var selectedCourt: Court? = null
    private var proceed: Boolean = true

    private val viewModel: BookOnlineViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentBookOnlineBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Book Online"
        viewModel.getVenueByLocation("0", "0", null)

        lifecycleScope.launchWhenStarted {
            viewModel.venueByLocationFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.result.forEach { venue ->
                            if (!dropDownOptions.containsKey(venue.venue_location)) {
                                dropDownOptions[venue.venue_location] = listOf(
                                    hashMapOf(
                                        venue.venue_name to venue.venue_sports.map { it.activity_name }
                                    )
                                )
                            } else {
                                dropDownOptions[venue.venue_location] =
                                    dropDownOptions[venue.venue_location]!!.plus(
                                        listOf(
                                            hashMapOf(
                                                venue.venue_name to venue.venue_sports.map { it.activity_name }
                                            )
                                        )
                                    )
                            }
                        }
                        binding.spinnerLocation.item = dropDownOptions.keys.toList()
                        binding.spinnerLocation.setSelection(0)
                        binding.spinnerLocation.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val location = parent?.getItemAtPosition(position).toString()
                                    binding.spinnerStadium.item =
                                        dropDownOptions[location]!!.map { it.keys.toList()[0] }
                                    binding.spinnerStadium.setSelection(0)

                                    binding.spinnerStadium.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                val venue =
                                                    parent?.getItemAtPosition(position).toString()
                                                binding.spinnerSports.item =
                                                    dropDownOptions[location]!!.filter { it.keys.toList()[0] == venue }[0][venue]
                                                if (binding.spinnerSports.item.isEmpty()) {
                                                    binding.spinnerSports.item =
                                                        listOf("No Sports Available")
                                                }
                                                binding.spinnerSports.setSelection(0)
                                                binding.spinnerSports.onItemSelectedListener =
                                                    object : AdapterView.OnItemSelectedListener {
                                                        override fun onItemSelected(
                                                            parent: AdapterView<*>?,
                                                            view: View?,
                                                            position: Int,
                                                            id: Long
                                                        ) {
                                                            val sport =
                                                                parent?.getItemAtPosition(position)
                                                                    .toString()

                                                            binding.rvCourts.adapter = null
                                                            binding.rvTimes.adapter = null
                                                            if (sport != "No Sports Available") {
                                                                val venueSelected =
                                                                    resource.result.filter { it.venue_name == venue }[0]
                                                                val sportId =
                                                                    venueSelected.venue_sports.filter { it.activity_name == sport }[0].aid
                                                                val venueId = venueSelected.sid
                                                                viewModel.getCourtList(
                                                                    venueId,
                                                                    sportId
                                                                )
                                                            } else {
                                                                Toast.makeText(
                                                                    context,
                                                                    "No Sports Available",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }

                                                        override fun onNothingSelected(parent: AdapterView<*>?) {
                                                            binding.spinnerSports.setSelection(0)
                                                        }
                                                    }
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                                binding.spinnerStadium.setSelection(0)
                                            }
                                        }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    binding.spinnerLocation.setSelection(0)
                                }
                            }

                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "onViewCreated: Loading")
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.courtListFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val courtAdapter = RecyclerAdapterCourts(resource.result.courts)
                        binding.rvCourts.adapter = courtAdapter
                        courtAdapter.setOnItemClickListener(object :
                            RecyclerAdapterCourts.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val court = resource.result.courts[position]
                                binding.rdGroup.removeAllViews()

                                val courtId = court.cid
                                val time = binding.tvTime.text.toString()
                                val date = binding.tvCalender.text.toString()
                                if (date != "Select Date" && time != "Select Time") {
                                    binding.tvFrequency.visibility = View.VISIBLE
                                    court.special_frequency.forEach {
                                        val radioButton = RadioButton(context)
                                        radioButton.text = it
                                        binding.rdGroup.addView(radioButton)
                                    }
                                    val temp = courtAdapter.selected
                                    courtAdapter.selected = position
                                    viewModel.getTimeSlots(courtId, date, time)
                                    selectedCourt = resource.result.courts[position]
                                    courtAdapter.notifyItemChanged(temp)
                                    courtAdapter.notifyItemChanged(position)
                                    binding.rvTimes.adapter = null
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please select date and time",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "onViewCreated: Loading")
                    }
                    else -> {}
                }
            }
        }

        binding.tvCalender.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.DatePickerTheme)
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now())
                        .build()
                )
                .build()

            datePicker.show(parentFragmentManager, "DATE_PICKER_TAG")

            datePicker.addOnPositiveButtonClickListener {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
                binding.tvCalender.text = date
            }
        }

        binding.tvTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(Calendar.HOUR_OF_DAY)
                .setMinute(Calendar.MINUTE)
                .setTheme(R.style.TimePickerTheme)
                .setTitleText("Select time")
                .build()

            timePicker.show(parentFragmentManager, "TIME_PICKER_TAG")

            timePicker.addOnPositiveButtonClickListener {
                val hour = timePicker.hour
                val minute = timePicker.minute
                val time = "%02d:%02d".format(hour, minute)
                if (binding.tvCalender.text == "Select Date") {
                    Toast.makeText(context, "Please select date first", Toast.LENGTH_SHORT).show()
                    return@addOnPositiveButtonClickListener
                }

                if (binding.tvCalender.text.toString() == SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(Date()) &&
                    time < SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                ) {
                    Toast.makeText(context, "Time cannot be in the past", Toast.LENGTH_SHORT).show()
                    return@addOnPositiveButtonClickListener
                }
                binding.tvTime.text = time
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.timeSlotFlow.collect {
                when (it) {
                    is Resource.Success -> {
                        val timeAdapter = RecyclerAdapterTimeSlots(it.result)
                        binding.rvTimes.adapter = timeAdapter
                        timeAdapter.setOnItemClickListener(object :
                            RecyclerAdapterTimeSlots.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                val timeSlot = it.result[position].tslot_id
                                val checkedRadioButtonId = binding.rdGroup.checkedRadioButtonId
                                if (checkedRadioButtonId == -1) {
                                    Toast.makeText(
                                        context,
                                        "Please select frequency",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return
                                }
                                val checkedRadioButton =
                                    binding.rdGroup.findViewById<RadioButton>(checkedRadioButtonId)
                                val frequency = checkedRadioButton.text.toString()
                                viewModel.makeGetTimeSlotReservation(
                                    timeSlot,
                                    selectedCourt!!.frequency,
                                    frequency.toInt()
                                )
                                Log.d(
                                    "TAG",
                                    "onItemClick: $timeSlot, ${selectedCourt!!.frequency}, $frequency"
                                )
                            }
                        })
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "onViewCreated: Loading")
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.reservationFlow.collect {
                when (it) {
                    is Resource.Success -> {
                        // navigate to cart fragment with list of time slots in bundle
                        val bundle = Bundle()
                        Log.d("TAG", "slots: ${it.result}")
                        bundle.putString("slotIds", it.result.datum)
                        proceed = if (proceed) {
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.cartFragment, bundle)
                            false
                        } else
                            true
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "onViewCreated: Loading")
                    }
                    else -> {}
                }
            }
        }
    }
}