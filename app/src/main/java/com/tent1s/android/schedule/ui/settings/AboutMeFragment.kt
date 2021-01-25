package com.tent1s.android.schedule.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentAboutMeBinding
import com.tent1s.android.schedule.databinding.FragmentSettingsBinding

class AboutMeFragment : Fragment() {


    private var _binding: FragmentAboutMeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_about_me,
                container,
                false
        )



        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}