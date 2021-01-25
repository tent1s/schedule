package com.tent1s.android.schedule.ui.timetable.timetablelist

import android.app.AlertDialog
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
import com.tent1s.android.schedule.repository.ScheduleRepository
import com.tent1s.android.schedule.ui.tasks.TasksAdapter
import com.tent1s.android.schedule.ui.tasks.taskslist.TasksFragmentDirections
import com.tent1s.android.schedule.ui.timetable.TimetableAdapter
import timber.log.Timber

class TimetableFragment : Fragment() {

    private lateinit var timetableViewModel: TimetableViewModel
    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!
    private lateinit var myRepository : ScheduleRepository


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTimetableBinding.inflate(inflater)


        myRepository = (requireActivity().application as ScheduleApplication).repository

        timetableViewModel =
                ViewModelProvider(this).get(TimetableViewModel::class.java)

        binding.viewModel = timetableViewModel

        timetableViewModel.setWeek( myRepository.weekId)

        myRepository.timetable.observe(viewLifecycleOwner){ list ->
            timetableViewModel.week.observe(viewLifecycleOwner){
                timetableViewModel.getTimetable(list, it)
            }
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TimetableAdapter {
            val navController = binding.root.findNavController()
            val week = timetableViewModel.week.value
            if (week != null) {
                navController.navigate(TimetableFragmentDirections.actionNavigationTimetableToNewTimeRow(it.id, week))
            }
        }
        binding.timetableList.adapter = adapter


        timetableViewModel.state.observe(viewLifecycleOwner){
            adapter.setData(it)
            if (it.isEmpty()){
                timetableViewModel.listIsEmpty()
            }else{
                timetableViewModel.listIsNotEmpty()
            }
        }



        timetableViewModel.navigateToSearch.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate == true) {
                val navController = binding.root.findNavController()
                val week = timetableViewModel.week.value
                if (week != null) {
                    navController.navigate(TimetableFragmentDirections.actionNavigationTimetableToNewTimeRow(-1L, week))
                }
                timetableViewModel.onNavigationToSearch()
            }
        }


        binding.timetableList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.floatingActionButtonTimetable.isShown) {
                    binding.floatingActionButtonTimetable.show()
                    binding.Week.visibility = View.VISIBLE
                }
                else
                    if (dy > 0 && binding.floatingActionButtonTimetable.isShown) {
                        binding.floatingActionButtonTimetable.hide()
                        binding.Week.visibility = View.GONE
                    }
            }
        })

        binding.textWeek.text = when (timetableViewModel.week.value){
            0 ->  "четная неделя"
            1 ->  "нечетная неделя"
            else -> "error"
        }
        binding.Week.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Выберете неделю")

            val dayOfWeek = arrayOf("Четная неделя", "Нечетная неделя")
            builder.setItems(dayOfWeek) { _, which ->
                when (which){
                    0 -> binding.textWeek.text = "четная неделя"
                    1 -> binding.textWeek.text = "нечетная неделя"
                }
                myRepository.setWeek(which)
                timetableViewModel.setWeek(which)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

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