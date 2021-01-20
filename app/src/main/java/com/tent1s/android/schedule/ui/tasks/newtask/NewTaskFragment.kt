package com.tent1s.android.schedule.ui.tasks.newtask

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentNewtaskBinding

import com.tent1s.android.schedule.utils.convertMonthToString
import com.tent1s.android.schedule.utils.shortToast
import timber.log.Timber
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

        val application = requireNotNull(this.activity).application
        val dataSource = ScheduleDatabase.getInstance(application).tasksDatabaseDao
        viewModelFactory = NewTaskViewModelFactory(dataSource, application)
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


    private fun getDate(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = context?.let { it ->
            DatePickerDialog(it,R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val monthNorm = monthOfYear + 1
                newTaskViewModel.getDate(dayOfMonth,monthNorm,year)
                binding.dateTask.text = "$dayOfMonth ${convertMonthToString(monthNorm)}"
            }, year, month, day)
        }
        dpd?.show()
    }
}