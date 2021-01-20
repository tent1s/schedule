package com.tent1s.android.schedule.ui.tasks.taskslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.ScheduleApplication
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentTasksBinding
import com.tent1s.android.schedule.ui.tasks.TasksAdapter
import com.tent1s.android.schedule.ui.timetable.TimetableAdapter
import com.tent1s.android.schedule.ui.timetable.timetablelist.TimetableViewModelFactory
import com.tent1s.android.schedule.utils.shortToast
import timber.log.Timber

class TasksFragment : Fragment() {

    private lateinit var tasksViewModel: TasksViewModel
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tasks,
            container,
            false
        )



        val myRepository = (requireActivity().application as ScheduleApplication).repository


        tasksViewModel =
                ViewModelProvider(this).get(TasksViewModel::class.java)
        binding.viewModel = tasksViewModel

        myRepository.tasks.observe(viewLifecycleOwner){
            tasksViewModel.getTasks(it)
            if (it.isEmpty()){
                tasksViewModel.listIsEmpty()
            }else{
                tasksViewModel.listIsNotEmpty()
            }
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        tasksViewModel.checkEmptyList.observe(viewLifecycleOwner) {
            if (it) {
                binding.textTasks.text = "Нажмите “+”, чтобы добавить"
            }else{
                binding.textTasks.text = ""
            }
        }

        val adapter = TasksAdapter {
            Timber.i("Clicked on item ${it.title}")
        }
        binding.taskList.adapter = adapter





        tasksViewModel.state.observe(viewLifecycleOwner){
            adapter.setData(it)
        }




        tasksViewModel.navigateToSearch.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate == true) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_navigation_tasks_to_newTaskFragment)
                tasksViewModel.onNavigationToSearch()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}