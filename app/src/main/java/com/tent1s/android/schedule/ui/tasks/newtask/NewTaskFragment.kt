package com.tent1s.android.schedule.ui.tasks.newtask

import android.os.Build
import android.os.Bundle
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
import java.util.*


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

        binding.viewModel = newTaskViewModel

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            newTaskViewModel.navigateToTasks.observe(viewLifecycleOwner) { shouldNavigate ->
                if (shouldNavigate == true) {
                    val navController = binding.root.findNavController()
                    navController.navigate(R.id.action_newTaskFragment_to_navigation_tasks)
                    newTaskViewModel.onNavigateToTasks()
                }
            }


            newTaskViewModel.saveSomeInf.observe(viewLifecycleOwner) { saveSomeInf ->
                if (saveSomeInf == true) {
                    val navController = binding.root.findNavController()
                    navController.navigate(R.id.action_newTaskFragment_to_navigation_tasks)
                    newTaskViewModel.onSaveButtonClickComplete()
                }
            }



            newTaskViewModel.timePickerDialogData.observe(viewLifecycleOwner) { display ->
                if (display == true) {
                    activity?.hideKeyboard()
                    getDate()
                    newTaskViewModel.getTimePickerDialogData()
                }
            }

            newTaskViewModel.errorToast.observe(viewLifecycleOwner){ display ->
                if (display == true) {
                    context?.shortToast("Вы ввели не всю информацию!!")
                    newTaskViewModel.errorEnd()
                }
            }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(){

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTheme(R.style.MaterialCalendarTheme);
        val picker = builder.build()
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager

        picker.show(fragmentManager, picker.toString())

        picker.addOnPositiveButtonClickListener {
            val dt = Instant.ofEpochSecond(it / 1000L).atZone(ZoneId.systemDefault()).toLocalDateTime()
            newTaskViewModel.getDate(dt)
            binding.dateTask.text = picker.headerText
        }
    }
}