package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
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
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentNewTimeRowBinding
import com.tent1s.android.schedule.ui.tasks.newtask.NewTaskFragmentArgs
import com.tent1s.android.schedule.ui.tasks.newtask.NewTaskViewModelFactory
import com.tent1s.android.schedule.utils.hideKeyboard
import com.tent1s.android.schedule.utils.shortToast
import timber.log.Timber


class NewTimeRowFragment : Fragment() {

    private lateinit var newTimeRowViewModel: NewTimeRowViewModel
    private var _binding: FragmentNewTimeRowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: NewTimeRowViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_time_row,
            container,
            false
        )

        val args = arguments?.let { NewTimeRowArgs.fromBundle(it) }
        val application = requireNotNull(this.activity).application
        val dataSource = ScheduleDatabase.getInstance(application).timetableDatabaseDao
        viewModelFactory = NewTimeRowViewModelFactory(dataSource, application, args!!.timetableId)
        newTimeRowViewModel =
            ViewModelProvider(this, viewModelFactory).get(NewTimeRowViewModel::class.java)

        binding.viewModel = newTimeRowViewModel

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        newTimeRowViewModel.saveTimeInf.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_newTimeRow_to_navigation_timetable)
                newTimeRowViewModel.onSaveTimeInfButtonClickComplete()
            }
        }




        newTimeRowViewModel.dayOfWeekButton.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                activity?.hideKeyboard()
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Выберете день недели")

                val dayOfWeek = arrayOf("Понидельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")
                builder.setItems(dayOfWeek) { _, which ->
                            when (which) {
                                0 -> binding.buttonDayOfWeek.text = "Понидельник"
                                1 -> binding.buttonDayOfWeek.text = "Вторник"
                                2 -> binding.buttonDayOfWeek.text = "Среда"
                                3 -> binding.buttonDayOfWeek.text = "Четверг"
                                4 -> binding.buttonDayOfWeek.text = "Пятница"
                                5 -> binding.buttonDayOfWeek.text = "Суббота"
                            }
                        }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                newTimeRowViewModel.onDayOfWeekButtonClickComplete()
            }
        }

        newTimeRowViewModel.colorButton.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                activity?.hideKeyboard()
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Выберете цвет")

                val color = arrayOf("Черный", "Синий", "Зеленый", "Желтый", "Красный")
                builder.setItems(color){ _, which ->
                            when (which) {
                                0 -> binding.colorButton.text = "Черный"
                                1 -> binding.colorButton.text = "Синий"
                                2 -> binding.colorButton.text = "Зеленый"
                                3 -> binding.colorButton.text = "Желтый"
                                4 -> binding.colorButton.text = "Красный"
                            }
                        }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                newTimeRowViewModel.onColorButtonClickComplete()
            }
        }



        newTimeRowViewModel.timetableStartTime.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                activity?.hideKeyboard()
                val hour = 0
                val minute = 0
                val tpd = context?.let { it ->
                    TimePickerDialog( it,R.style.DialogTheme, TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
                        binding.buttonTimeStart.text = "$hour:$minute"
                    }, hour, minute, true)
                }
                tpd?.show()

                newTimeRowViewModel.onTimetableStartTimeButtonClickComplete()
            }
        }

        newTimeRowViewModel.timetableEndTime.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                activity?.hideKeyboard()
                val hour = 0
                val minute = 0
                val tpd = context?.let { it ->
                    TimePickerDialog( it, R.style.DialogTheme, TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
                        binding.buttonTimeEnd.text = "$hour:$minute"
                    }, hour, minute, true)
                }
                tpd?.show()

                newTimeRowViewModel.onTimetableEndTimeButtonClickComplete()
            }
        }

        newTimeRowViewModel.errorNullDate.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                context?.shortToast("Вы ввели не всю информацию!!")
                newTimeRowViewModel.errorNullDateComplete()
            }
        }

        newTimeRowViewModel.errorInvalidTime.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                context?.shortToast("Конечная дата меньше начальной!")
                newTimeRowViewModel.errorInvalidTimeComplete()
            }
        }
    }

}