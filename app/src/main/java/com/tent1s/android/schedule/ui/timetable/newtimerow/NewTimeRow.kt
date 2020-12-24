package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.databinding.FragmentNewTimeRowBinding



class NewTimeRow : Fragment() {

    private lateinit var binding: FragmentNewTimeRowBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_time_row,
            container,
            false
        )

        return binding.root
    }
}