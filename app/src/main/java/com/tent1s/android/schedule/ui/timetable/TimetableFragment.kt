package com.tent1s.android.schedule.ui.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.databinding.FragmentTimetableBinding

class TimetableFragment : Fragment() {

    private lateinit var timetableViewModel: TimetableViewModel
    private lateinit var binding: FragmentTimetableBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_timetable,
            container,
            false
        )

        timetableViewModel =
                ViewModelProvider(this).get(TimetableViewModel::class.java)


        timetableViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textTimetable.text = it
        })

        binding.floatingActionButtonTimetable.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.action_navigation_timetable_to_newTimeRow)
        }

        return binding.root
    }
}