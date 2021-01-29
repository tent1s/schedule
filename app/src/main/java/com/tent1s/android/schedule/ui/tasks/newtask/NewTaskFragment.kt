package com.tent1s.android.schedule.ui.tasks.newtask

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentNewtaskBinding
import com.tent1s.android.schedule.utils.hideKeyboard
import com.tent1s.android.schedule.utils.shortToast
import java.time.Instant
import java.time.ZoneId


class NewTaskFragment : Fragment() {

    private lateinit var newTaskViewModel: NewTaskViewModel

    private lateinit var viewModelFactory: NewTaskViewModelFactory
    private var _binding: FragmentNewtaskBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_newtask,
                container,
                false
        )

        val args = arguments?.let { NewTaskFragmentArgs.fromBundle(it) }
        val application = requireNotNull(this.activity).application
        val dataSource = ScheduleDatabase.getInstance(application).tasksDatabaseDao
        viewModelFactory = NewTaskViewModelFactory(dataSource, application, args!!.taskId)
        newTaskViewModel = ViewModelProvider(this, viewModelFactory)
                .get(NewTaskViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.delTask.setOnClickListener {
            newTaskViewModel.onDelButtonClick()

            val navController = binding.root.findNavController()
            navController.navigate(R.id.action_newTaskFragment_to_navigation_tasks)
        }




        binding.saveTask.setOnClickListener{
            if (newTaskViewModel.isValid.value == true) {
                newTaskViewModel.saveInf()
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_newTaskFragment_to_navigation_tasks)
            }else{
                context?.shortToast("Вы ввели не всю информацию!!")
            }
        }


        binding.dateTask.setOnClickListener {
            activity?.hideKeyboard()

            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTheme(R.style.MaterialCalendarTheme)
            builder.setTitleText("Выбор даты")
            val picker = builder.build()
            val fragmentManager = (activity as FragmentActivity).supportFragmentManager

            picker.show(fragmentManager, picker.toString())

            picker.addOnPositiveButtonClickListener {
                val dt = Instant.ofEpochSecond(it / 1000L).atZone(ZoneId.systemDefault()).toLocalDateTime()
                newTaskViewModel.getDate(dt)
                binding.dateTask.text = picker.headerText
            }
        }

        binding.dateTask.addTextChangedListener( newTaskViewModel.dateWatcher())
        newTaskViewModel.date.observe(viewLifecycleOwner){
            binding.dateTask.text = it
        }

        binding.isTaskDone.setOnCheckedChangeListener { _, boolean ->
            newTaskViewModel.saveIsDoneTask(boolean)
        }

        newTaskViewModel.complete.observe(viewLifecycleOwner){
            binding.isTaskDone.isChecked = it
        }

        binding.taskTitleInput.addTextChangedListener( newTaskViewModel.titleWatcher() )
        newTaskViewModel.titleLive.observe(viewLifecycleOwner){
            binding.taskTitleInput.setText(it)
        }

        binding.taskAboutInput.addTextChangedListener( newTaskViewModel.aboutWatcher() )
        newTaskViewModel.aboutLive.observe(viewLifecycleOwner){
            binding.taskAboutInput.setText(it)
        }

    }
}

