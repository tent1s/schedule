package com.tent1s.android.schedule.ui.newtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.databinding.FragmentNewtaskBinding


class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewtaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_newtask,
            container,
            false
        )

        return binding.root
    }
}