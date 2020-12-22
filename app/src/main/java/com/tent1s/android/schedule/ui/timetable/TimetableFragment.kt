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
import androidx.navigation.findNavController
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.databinding.FragmentTasksBinding
import com.tent1s.android.schedule.databinding.FragmentTimetableBinding
import com.tent1s.android.schedule.ui.tasks.TasksViewModel

class TimetableFragment : Fragment() {

    private lateinit var timetableViewModel: TimetableViewModel
    private lateinit var binding: FragmentTimetableBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_timetable, container, false)
        val binding = FragmentTimetableBinding.inflate(inflater)

        timetableViewModel =
                ViewModelProvider(this).get(TimetableViewModel::class.java)

        binding.viewModel = timetableViewModel

        timetableViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textTimetable.text = it
        })

        timetableViewModel.navigateToSearch.observe(viewLifecycleOwner,
                Observer<Boolean> { shouldNavigate ->
                    if (shouldNavigate == true) {
                        val navController = binding.root.findNavController()
                        navController.navigate(R.id.action_navigation_timetable_to_newTimeRow)
                        timetableViewModel.onNavigationToSearch()
                    }
                })
        return binding.root
    }
}