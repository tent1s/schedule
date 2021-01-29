package com.tent1s.android.schedule.ui.tasks.taskslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.ScheduleApplication
import com.tent1s.android.schedule.databinding.FragmentTasksBinding
import com.tent1s.android.schedule.ui.tasks.TasksAdapter


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
            val navController = binding.root.findNavController()
            navController.navigate(TasksFragmentDirections.actionNavigationTasksToNewTaskFragment(it.id))
        }
        binding.taskList.adapter = adapter





        tasksViewModel.state.observe(viewLifecycleOwner){
            adapter.setData(it)
        }




        binding.taskList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.floatingActionButtonTask.isShown) binding.floatingActionButtonTask.show() else
                    if (dy > 0 && binding.floatingActionButtonTask.isShown) binding.floatingActionButtonTask.hide()
            }
        })


        binding.floatingActionButtonTask.setOnClickListener {
                val navController = binding.root.findNavController()
                navController.navigate(TasksFragmentDirections.actionNavigationTasksToNewTaskFragment(-1L))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}