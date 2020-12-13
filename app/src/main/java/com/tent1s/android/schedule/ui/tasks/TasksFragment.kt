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
        binding = DataBindingUtil.inflate(
            inflater,
           R.layout.fragment_tasks,
            container,
            false
        )

        tasksViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)

        tasksViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textTasks.text = it
        })

        binding.floatingActionButtonTask.setOnClickListener { view ->
           Navigation.findNavController(view).navigate(R.id.action_navigation_tasks_to_newTaskFragment)
        }

        return binding.root
    }
}