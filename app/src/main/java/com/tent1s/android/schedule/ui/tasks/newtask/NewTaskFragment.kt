package com.tent1s.android.schedule.ui.tasks.newtask

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.databinding.FragmentNewtaskBinding
import com.tent1s.android.schedule.utils.convertMonthToString
import com.tent1s.android.schedule.utils.shortToast
import timber.log.Timber
import java.util.*


class NewTaskFragment : Fragment() {

    private lateinit var newTaskViewModel: NewTaskViewModel


    private var _binding: FragmentNewtaskBinding? = null
    private val binding get() = _binding!!

    private var dayTask : Int = 0
    private var monthTask :Int = 0
    private var yearTask :Int = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_newtask,
                container,
                false
        )
        newTaskViewModel = ViewModelProvider(this)
                .get(NewTaskViewModel::class.java)

        binding.viewModel = newTaskViewModel

        observeToSaveInf()
        observeToTasksNavigate()
        observeTimePickerDialogData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun observeToTasksNavigate(){
        newTaskViewModel.navigateToTasks.observe(viewLifecycleOwner,
                Observer<Boolean> { shouldNavigate ->
                    if (shouldNavigate == true) {
                        val navController = binding.root.findNavController()
                        navController.navigate(R.id.action_newTaskFragment_to_navigation_tasks)
                        newTaskViewModel.onNavigateToTasks()
                    }
                })
    }

    private fun observeToSaveInf(){
        newTaskViewModel.saveSomeInf.observe(viewLifecycleOwner,
                Observer<Boolean> { saveSomeInf ->
                    if (saveSomeInf == true) {
                        val title = binding.taskTitleInput.text.toString()
                        val about = binding.taskAboutInput.text.toString()
                        val isDone = binding.isTaskDone.isChecked
                        if(title.isNotBlank() && about.isNotBlank() && dayTask != 0 && monthTask != 0 && yearTask != 0 ){

                            //TODO: вызов фун из модуля для добавления в бд
                            Timber.i("Title : $title  about : $about  date: $dayTask.$monthTask.$yearTask  isDone:$isDone")


                            val navController = binding.root.findNavController()
                            navController.navigate(R.id.action_newTaskFragment_to_navigation_tasks)
                        }else {
                            context?.shortToast("Вы ввели не всю информацию!!")
                        }
                        newTaskViewModel.onSaveButtonClickComplete()
                    }
                })
    }


    private fun observeTimePickerDialogData() {
        newTaskViewModel.timePickerDialogData.observe(viewLifecycleOwner,
                Observer<Boolean> { display ->
            if (display == true){
                getDate()
                newTaskViewModel.getTimePickerDialogData()
            }
        })
    }

    private fun getDate(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = context?.let { it ->
                DatePickerDialog(it, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    dayTask = dayOfMonth
                    monthTask = monthOfYear + 1
                    yearTask = year
                    binding.dateTask.text = "$dayTask ${convertMonthToString(monthTask)}"
                }, year, month, day)
            }
            dpd?.show()
        }

}