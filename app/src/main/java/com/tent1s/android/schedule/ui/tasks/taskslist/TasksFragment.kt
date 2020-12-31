package com.tent1s.android.schedule.ui.tasks.taskslist

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
import com.tent1s.android.schedule.ui.tasks.TasksAdapter
import com.tent1s.android.schedule.ui.timetable.TimetableAdapter
import com.tent1s.android.schedule.ui.timetable.timetablelist.TimetableViewModelFactory

class TasksFragment : Fragment() {

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModelFactory: TasksViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        val binding = FragmentTasksBinding.inflate(inflater)



        val myRepository = (requireActivity().application as ScheduleApplication).repository
        viewModelFactory = TasksViewModelFactory(myRepository)
        tasksViewModel =
                ViewModelProvider(this, viewModelFactory ).get(TasksViewModel::class.java)

        binding.viewModel = tasksViewModel

        tasksViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textTasks.text = it
        })


        val adapter = TasksAdapter(tasksViewModel.tasks)
        binding.taskList.adapter = adapter



        tasksViewModel.navigateToSearch.observe(viewLifecycleOwner,
                Observer<Boolean> { shouldNavigate ->
                    if (shouldNavigate == true) {
                        val navController = binding.root.findNavController()
                        navController.navigate(R.id.action_navigation_tasks_to_newTaskFragment)
                        tasksViewModel.onNavigationToSearch()
                    }
                })

        return binding.root
    }
}