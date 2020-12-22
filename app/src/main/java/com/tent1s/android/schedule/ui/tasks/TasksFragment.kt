package com.tent1s.android.schedule.ui.tasks

import android.os.Bundle
import android.renderscript.ScriptGroup
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
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import com.google.android.material.snackbar.Snackbar
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.databinding.FragmentTasksBinding
import com.tent1s.android.schedule.ui.newtask.NewTaskFragment

class TasksFragment : Fragment() {

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var binding: FragmentTasksBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        val binding = FragmentTasksBinding.inflate(inflater)

        tasksViewModel =
                ViewModelProvider(this).get(TasksViewModel::class.java)

        binding.viewModel = tasksViewModel

        tasksViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textTasks.text = it
        })

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