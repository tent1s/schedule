package com.tent1s.android.schedule.ui.timetable.timetablelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.ScheduleApplication
import com.tent1s.android.schedule.databinding.FragmentTasksBinding
import com.tent1s.android.schedule.databinding.FragmentTimetableBinding
import com.tent1s.android.schedule.ui.timetable.TimetableAdapter

class TimetableFragment : Fragment() {

    private lateinit var timetableViewModel: TimetableViewModel
    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: TimetableViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTimetableBinding.inflate(inflater)

        val myRepository = (requireActivity().application as ScheduleApplication).repository
        viewModelFactory = TimetableViewModelFactory(myRepository)
        timetableViewModel =
                ViewModelProvider(this,viewModelFactory).get(TimetableViewModel::class.java)

        binding.viewModel = timetableViewModel



        val adapter = TimetableAdapter(timetableViewModel.timetable)
        binding.timetableList.adapter = adapter



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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}