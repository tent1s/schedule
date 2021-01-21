package com.tent1s.android.schedule.ui.timetable.timetablelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.ScheduleApplication
import com.tent1s.android.schedule.databinding.FragmentTasksBinding
import com.tent1s.android.schedule.databinding.FragmentTimetableBinding
import com.tent1s.android.schedule.ui.timetable.TimetableAdapter

class TimetableFragment : Fragment() {

    private lateinit var timetableViewModel: TimetableViewModel
    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTimetableBinding.inflate(inflater)

        val myRepository = (requireActivity().application as ScheduleApplication).repository


        timetableViewModel =
                ViewModelProvider(this).get(TimetableViewModel::class.java)

        binding.viewModel = timetableViewModel

        myRepository.timetable.observe(viewLifecycleOwner){
            timetableViewModel.getTimetable(it)
            if (it.isEmpty()){
                timetableViewModel.listIsEmpty()
            }else{
                timetableViewModel.listIsNotEmpty()
            }
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TimetableAdapter()
        binding.timetableList.adapter = adapter

        timetableViewModel.state.observe(viewLifecycleOwner){
            adapter.setData(it)
        }


        timetableViewModel.navigateToSearch.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate == true) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_navigation_timetable_to_newTimeRow)
                timetableViewModel.onNavigationToSearch()
            }
        }


        binding.timetableList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.floatingActionButtonTimetable.isShown) binding.floatingActionButtonTimetable.show() else
                    if (dy > 0 && binding.floatingActionButtonTimetable.isShown) binding.floatingActionButtonTimetable.hide()
            }
        })

        timetableViewModel.checkEmptyList.observe(viewLifecycleOwner) {
            if (it) {
                binding.textTimetable.text = "Нажмите “+”, чтобы добавить"
            }else{
                binding.textTimetable.text = ""
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}