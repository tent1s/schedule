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
import com.tent1s.android.schedule.databinding.FragmentNewTimeRowBinding
import com.tent1s.android.schedule.utils.shortToast
import timber.log.Timber


class NewTimeRow : Fragment() {

    var dayOfWeek = -1
    var startMinute = -1
    var endMinute = -1
    var startHour = -1
    var endHour = -1
    var color = -1


    private lateinit var newTimeRowViewModel: NewTimeRowViewModel
    private var _binding: FragmentNewTimeRowBinding? = null
    private val binding get() = _binding!!

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

        newTimeRowViewModel =
            ViewModelProvider(this).get(NewTimeRowViewModel::class.java)

        binding.viewModel = newTimeRowViewModel



        newTimeRowViewModel.saveTimeInf.observe(viewLifecycleOwner,
            Observer<Boolean> { display ->
                if (display == true) {
                    val title = binding.inputTitle.text.toString()
                    val about = binding.inputAbout.text.toString()
                    if (title.isNotBlank() && about.isNotBlank() && dayOfWeek != -1 && startMinute != -1  && endMinute != -1 && startHour != -1 && endHour != -1 && color != -1){
                        if ((startHour > endHour) || ((startHour == endHour) && (startMinute > endMinute))){
                            context?.shortToast("Начальное время больше конечного!")
                        }else{

                            Timber.i("День недели: $dayOfWeek Начальное время: $startHour:$startMinute Конечное время: $endHour:$endMinute Заголовок: $title Описание: $about Цвет: $color")

                            val navController = binding.root.findNavController()
                            navController.navigate(R.id.action_newTimeRow_to_navigation_timetable)
                        }
                    }else{
                        context?.shortToast("Вы ввели не всю информацию!!")
                    }
                    newTimeRowViewModel.onSaveTimeInfButtonClickComplete()
                }
            })




        newTimeRowViewModel.dayOfWeekButton.observe(viewLifecycleOwner,
            Observer<Boolean> { display ->
                if (display == true) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setTitle("Выберете день недели")

                    val animals = arrayOf("Понидельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")
                    builder.setItems(animals,
                        DialogInterface.OnClickListener { _, which ->
                            when (which) {
                                0 -> binding.buttonDayOfWeek.text = "Понидельник"
                                1 -> binding.buttonDayOfWeek.text = "Вторник"
                                2 -> binding.buttonDayOfWeek.text = "Среда"
                                3 -> binding.buttonDayOfWeek.text = "Четверг"
                                4 -> binding.buttonDayOfWeek.text = "Пятница"
                                5 -> binding.buttonDayOfWeek.text = "Суббота"
                            }
                            dayOfWeek = which
                        })

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    newTimeRowViewModel.onDayOfWeekButtonClickComplete()
                }
            })

        newTimeRowViewModel.colorButton.observe(viewLifecycleOwner,
                Observer<Boolean> { display ->
                    if (display == true) {
                        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                        builder.setTitle("Выберете цвет")

                        val animals = arrayOf("Черный", "Синий", "Зеленый", "Желтый", "Красный")
                        builder.setItems(animals,
                                DialogInterface.OnClickListener { _, which ->
                                    when (which) {
                                        0 -> binding.colorButton.text = "Черный"
                                        1 -> binding.colorButton.text = "Синий"
                                        2 -> binding.colorButton.text = "Зеленый"
                                        3 -> binding.colorButton.text = "Желтый"
                                        4 -> binding.colorButton.text = "Красный"
                                    }
                                    color = which
                                })

                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        newTimeRowViewModel.onSaveTimeInfButtonClickComplete()
                    }
                })



        newTimeRowViewModel.timetableStartTime.observe(viewLifecycleOwner,
            Observer<Boolean> { display ->
                if (display == true) {
                    val hour = 0
                    val minute = 0
                    val tpd = context?.let { it ->
                        TimePickerDialog( it, TimePickerDialog.OnTimeSetListener{ view, hour, minute ->
                            binding.buttonTimeStart.text = "$hour:$minute"
                            startHour = hour
                            startMinute = minute
                        }, hour, minute, true)
                    }
                    tpd?.show()

                    newTimeRowViewModel.onTimetableStartTimeButtonClickComplete()
                }
            })

        newTimeRowViewModel.timetableEndTime.observe(viewLifecycleOwner,
            Observer<Boolean> { display ->
                if (display == true) {
                    val hour = 0
                    val minute = 0
                    val tpd = context?.let { it ->
                        TimePickerDialog( it, TimePickerDialog.OnTimeSetListener{ view, hour, minute ->
                            binding.buttonTimeEnd.text = "$hour:$minute"
                            endHour = hour
                            endMinute = minute
                        }, hour, minute, true)
                    }
                    tpd?.show()

                    newTimeRowViewModel.onTimetableEndTimeButtonClickComplete()
                }
            })

        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}